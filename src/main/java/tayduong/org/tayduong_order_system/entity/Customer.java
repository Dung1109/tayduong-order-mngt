package tayduong.org.tayduong_order_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    private String customerId;
    private String customerName;
    private String address;
    private String city;
    private String taxCode;
    private String phone;
    private boolean isInactive;
}
