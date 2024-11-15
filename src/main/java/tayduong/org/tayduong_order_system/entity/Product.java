package tayduong.org.tayduong_order_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String productId;
    private String productName;
    
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    private String mainUnit;
    private boolean isInactive;
}
