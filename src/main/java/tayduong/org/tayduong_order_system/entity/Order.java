package tayduong.org.tayduong_order_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", schema = "tayduong_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(generator = "order-id-generator")
    @GenericGenerator(
        name = "order-id-generator",
        strategy = "tayduong.org.tayduong_order_system.generator.OrderIdGenerator"
    )
    @Column(name = "order_id")
    private String orderId;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private LocalDate paymentDate;
    private String paymentTerms;
    
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal vatAmount;
    private BigDecimal totalPayment;
    
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar", name = "status")
    private OrderStatus status;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    
    public void addOrderDetail(OrderDetail detail) {
        orderDetails.add(detail);
        detail.setOrder(this);
    }
    
    public void removeOrderDetail(OrderDetail detail) {
        orderDetails.remove(detail);
        detail.setOrder(null);
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (this.customer != null) {
            this.customerName = this.customer.getCustomerName();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (this.customer != null) {
            this.customerName = this.customer.getCustomerName();
        }
    }
}
