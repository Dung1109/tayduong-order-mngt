package tayduong.org.tayduong_order_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private String employeeId;
    
    @Column(unique = true)
    private String username;
    
    private String employeeName;
    
    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;
    
    private String managerName;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    
    private String departmentName;
}
