package tayduong.org.tayduong_order_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tayduong.org.tayduong_order_system.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
