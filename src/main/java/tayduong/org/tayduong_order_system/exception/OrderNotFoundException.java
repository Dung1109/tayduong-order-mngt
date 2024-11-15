package tayduong.org.tayduong_order_system.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Order not found with ID: " + orderId);
    }
}
