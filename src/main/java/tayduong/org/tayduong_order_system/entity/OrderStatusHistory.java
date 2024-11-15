package tayduong.org.tayduong_order_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_status_history", schema = "tayduong_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "old_status")
    private OrderStatus oldStatus;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    private OrderStatus newStatus;
    
    @ManyToOne
    @JoinColumn(name = "changed_by")
    private Employee changedBy;
    
    @Column(name = "changed_at")
    private LocalDateTime changedAt;
    
    private String notes;
    
    @PrePersist
    protected void onCreate() {
        changedAt = LocalDateTime.now();
    }
}
