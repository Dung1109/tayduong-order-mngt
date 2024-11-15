package tayduong.org.tayduong_order_system.entity;

public enum OrderStatus {
    DRAFT,                           // Nháp
    PENDING_ACCOUNTANT_APPROVAL,     // Chờ Kế Toán chấp nhận
    PENDING_WAREHOUSE_APPROVAL,      // Chờ Thủ Kho chấp nhận
    PENDING_DELIVERY,                // Chờ Giao hàng
    COMPLETED                        // Hoàn thành
}
