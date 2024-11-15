package tayduong.org.tayduong_order_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tayduong.org.tayduong_order_system.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findByOrderOrderId(String orderId);
    List<OrderDetail> findByEmployeeEmployeeId(String employeeId);
    List<OrderDetail> findByProductProductId(String productId);
}
