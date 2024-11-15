package tayduong.org.tayduong_order_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tayduong.org.tayduong_order_system.entity.OrderStatusHistory;

import java.util.List;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    List<OrderStatusHistory> findByOrderOrderIdOrderByChangedAtDesc(String orderId);
    List<OrderStatusHistory> findByChangedByEmployeeId(String employeeId);
}
