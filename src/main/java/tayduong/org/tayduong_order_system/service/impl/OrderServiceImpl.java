package tayduong.org.tayduong_order_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tayduong.org.tayduong_order_system.config.KafkaTopics;
import tayduong.org.tayduong_order_system.dto.OrderDTO;
import tayduong.org.tayduong_order_system.dto.OrderStatusChangeDTO;
import tayduong.org.tayduong_order_system.entity.Employee;
import tayduong.org.tayduong_order_system.entity.Order;
import tayduong.org.tayduong_order_system.entity.OrderStatus;
import tayduong.org.tayduong_order_system.entity.OrderStatusHistory;
import tayduong.org.tayduong_order_system.exception.EmployeeNotFoundException;
import tayduong.org.tayduong_order_system.exception.InvalidOrderStatusTransitionException;
import tayduong.org.tayduong_order_system.exception.OrderNotFoundException;
import tayduong.org.tayduong_order_system.mapper.OrderMapper;
import tayduong.org.tayduong_order_system.repository.EmployeeRepository;
import tayduong.org.tayduong_order_system.repository.OrderRepository;
import tayduong.org.tayduong_order_system.repository.OrderStatusHistoryRepository;
import tayduong.org.tayduong_order_system.service.OrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository statusHistoryRepository;
    private final EmployeeRepository employeeRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final OrderMapper orderMapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        order.setStatus(OrderStatus.DRAFT);

        // Set up bidirectional relationship
        if (order.getOrderDetails() != null) {
            Order finalOrder = order;
            order.getOrderDetails().forEach(detail -> {
                detail.setOrder(finalOrder);
            });
        }

        order = orderRepository.save(order);

        // Send Kafka message
        kafkaTemplate.send(KafkaTopics.ORDER_CREATED, orderMapper.toDto(order));

        return orderMapper.toDto(order);
    }

    @Override
    public OrderDTO updateOrder(String orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        orderMapper.updateOrderFromDto(orderDTO, order);
        order = orderRepository.save(order);

        // Send Kafka message
        kafkaTemplate.send(KafkaTopics.ORDER_UPDATED, orderMapper.toDto(order));

        return orderMapper.toDto(order);
    }

    @Override
    public OrderDTO getOrder(String orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDTO> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerCustomerId(customerId)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate)
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDTO changeOrderStatus(OrderStatusChangeDTO statusChangeDTO) {
        Order order = orderRepository.findById(statusChangeDTO.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(statusChangeDTO.getOrderId()));

        Employee employee = employeeRepository.findById(statusChangeDTO.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(statusChangeDTO.getEmployeeId()));

        // Validate status transition
        validateStatusTransition(order.getStatus(), statusChangeDTO.getNewStatus());

        // Create status history record
        OrderStatusHistory statusHistory = new OrderStatusHistory();
        statusHistory.setOrder(order);
        statusHistory.setOldStatus(order.getStatus());
        statusHistory.setNewStatus(statusChangeDTO.getNewStatus());
        statusHistory.setChangedBy(employee);
        statusHistory.setNotes(statusChangeDTO.getNotes());

        // Update order status
        order.setStatus(statusChangeDTO.getNewStatus());

        // Save changes
        statusHistoryRepository.save(statusHistory);
        order = orderRepository.save(order);

        // Send Kafka message
        kafkaTemplate.send(KafkaTopics.ORDER_STATUS_CHANGED, orderMapper.toDto(order));

        return orderMapper.toDto(order);
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        // Add your status transition validation logic here
        // For example:
        if (currentStatus == OrderStatus.COMPLETED) {
            throw new InvalidOrderStatusTransitionException(currentStatus, newStatus);
        }
        // Add more validation rules as needed
    }
}
