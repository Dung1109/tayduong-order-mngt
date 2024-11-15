package tayduong.org.tayduong_order_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tayduong.org.tayduong_order_system.dto.OrderDTO;
import tayduong.org.tayduong_order_system.dto.OrderStatusChangeDTO;
import tayduong.org.tayduong_order_system.entity.OrderStatus;
import tayduong.org.tayduong_order_system.service.OrderService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs for managing orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get all orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping
    @Operation(summary = "Create a new order")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @PutMapping("/{orderId}")
    @Operation(summary = "Update an existing order")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable String orderId,
            @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, orderDTO));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Delete an order")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get orders by customer ID")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get orders by status")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get orders by date range")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(orderService.getOrdersByDateRange(startDate, endDate));
    }

    @PostMapping("/{orderId}/status")
    @Operation(summary = "Change order status")
    public ResponseEntity<OrderDTO> changeOrderStatus(
            @PathVariable String orderId,
            @RequestBody OrderStatusChangeDTO statusChangeDTO) {
        statusChangeDTO.setOrderId(orderId);
        return ResponseEntity.ok(orderService.changeOrderStatus(statusChangeDTO));
    }
}
