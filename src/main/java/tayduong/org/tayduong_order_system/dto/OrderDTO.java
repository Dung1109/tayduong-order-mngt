package tayduong.org.tayduong_order_system.dto;

import lombok.Data;
import tayduong.org.tayduong_order_system.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;
    private String customerId;
    private String customerName;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private LocalDate paymentDate;
    private String paymentTerms;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal vatAmount;
    private BigDecimal totalPayment;
    private OrderStatus status;
    private List<OrderDetailDTO> orderDetails;
}
