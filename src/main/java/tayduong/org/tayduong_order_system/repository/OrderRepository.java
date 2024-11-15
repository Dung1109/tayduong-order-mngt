package tayduong.org.tayduong_order_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tayduong.org.tayduong_order_system.entity.Order;
import tayduong.org.tayduong_order_system.entity.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByCustomerCustomerId(String customerId);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT o FROM Order o WHERE o.status = ?1 AND o.customer.customerId = ?2")
    List<Order> findByStatusAndCustomerId(OrderStatus status, String customerId);
}
