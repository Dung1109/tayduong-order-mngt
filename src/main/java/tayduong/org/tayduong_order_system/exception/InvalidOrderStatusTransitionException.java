package tayduong.org.tayduong_order_system.exception;

import tayduong.org.tayduong_order_system.entity.OrderStatus;

public class InvalidOrderStatusTransitionException extends RuntimeException {
    public InvalidOrderStatusTransitionException(OrderStatus currentStatus, OrderStatus newStatus) {
        super("Invalid order status transition from " + currentStatus + " to " + newStatus);
    }
}
