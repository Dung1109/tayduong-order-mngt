package tayduong.org.tayduong_order_system.service;

import tayduong.org.tayduong_order_system.dto.OrderDTO;
import tayduong.org.tayduong_order_system.dto.OrderStatusChangeDTO;
import tayduong.org.tayduong_order_system.entity.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(String orderId, OrderDTO orderDTO);
    OrderDTO getOrder(String orderId);
    void deleteOrder(String orderId);
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getOrdersByCustomer(String customerId);
    List<OrderDTO> getOrdersByStatus(OrderStatus status);
    List<OrderDTO> getOrdersByDateRange(LocalDate startDate, LocalDate endDate);
    OrderDTO changeOrderStatus(OrderStatusChangeDTO statusChangeDTO);
}
