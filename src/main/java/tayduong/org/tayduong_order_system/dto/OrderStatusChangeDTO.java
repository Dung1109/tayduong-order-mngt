package tayduong.org.tayduong_order_system.dto;

import lombok.Data;
import tayduong.org.tayduong_order_system.entity.OrderStatus;

@Data
public class OrderStatusChangeDTO {
    private String orderId;
    private OrderStatus newStatus;
    private String employeeId;
    private String notes;
}
