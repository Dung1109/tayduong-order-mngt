package tayduong.org.tayduong_order_system.mapper;

import org.mapstruct.*;
import tayduong.org.tayduong_order_system.dto.OrderDTO;
import tayduong.org.tayduong_order_system.dto.OrderDetailDTO;
import tayduong.org.tayduong_order_system.entity.Order;
import tayduong.org.tayduong_order_system.entity.OrderDetail;
import tayduong.org.tayduong_order_system.entity.Customer;
import tayduong.org.tayduong_order_system.entity.Product;
import tayduong.org.tayduong_order_system.entity.Employee;
import tayduong.org.tayduong_order_system.entity.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    @Mapping(target = "customerId", source = "customer.customerId")
    OrderDTO toDto(Order order);

    @Mapping(target = "customer", expression = "java(createCustomerReference(orderDTO.getCustomerId(), orderDTO.getCustomerName()))")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Order toEntity(OrderDTO orderDTO);

    @AfterMapping
    default void linkOrderDetails(@MappingTarget Order order, OrderDTO orderDTO) {
        if (orderDTO.getOrderDetails() != null) {
            List<OrderDetail> details = new ArrayList<>();
            orderDTO.getOrderDetails().forEach(detailDTO -> {
                OrderDetail detail = toOrderDetail(detailDTO);
                detail.setOrder(order);
                detail.setCustomer(order.getCustomer());
                details.add(detail);
            });
            order.setOrderDetails(details);
        }
    }

    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "employeeId", source = "employee.employeeId")
    OrderDetailDTO toDto(OrderDetail orderDetail);

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", expression = "java(createProductReference(orderDetailDTO.getProductId()))")
    @Mapping(target = "employee", expression = "java(createEmployeeReference(orderDetailDTO.getEmployeeId()))")
    OrderDetail toOrderDetail(OrderDetailDTO orderDetailDTO);

    @Mapping(target = "orderDetails", ignore = true)
    void updateOrderFromDto(OrderDTO orderDTO, @MappingTarget Order order);

    @AfterMapping
    default void updateOrderDetailsFromDto(OrderDTO orderDTO, @MappingTarget Order order) {
        List<OrderDetail> details = new ArrayList<>();
        if (orderDTO.getOrderDetails() != null) {
            orderDTO.getOrderDetails().forEach(detailDTO -> {
                OrderDetail detail = toOrderDetail(detailDTO);
                detail.setOrder(order);
                detail.setCustomer(order.getCustomer());
                details.add(detail);
            });
        }
        order.getOrderDetails().clear();
        order.getOrderDetails().addAll(details);
    }

    default Customer createCustomerReference(String customerId, String customerName) {
        if (customerId == null) return null;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName(customerName);
        return customer;
    }

    default Product createProductReference(String productId) {
        if (productId == null) return null;
        Product product = new Product();
        product.setProductId(productId);
        return product;
    }

    default Employee createEmployeeReference(String employeeId) {
        if (employeeId == null) return null;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        return employee;
    }
}
