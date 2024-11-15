package tayduong.org.tayduong_order_system.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderDetailDTO {
    private String orderDetailId;
    private String orderId;
    private String productId;
    private String productName;
    private String batchNumber;
    private LocalDate expiryDate;
    private String unit;
    private BigDecimal quantity;
    private BigDecimal unitPriceWithVat;
    private BigDecimal amount;
    private BigDecimal discountPercentage;
    private BigDecimal discountAmount;
    private BigDecimal vatPercentage;
    private BigDecimal vatAmount;
    private BigDecimal totalPayment;
    private String employeeId;
    private String employeeName;
}
