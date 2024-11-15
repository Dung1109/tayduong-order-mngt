package tayduong.org.tayduong_order_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    private String orderDetailId;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
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
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    
    private String employeeName;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
