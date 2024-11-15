package tayduong.org.tayduong_order_system.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String employeeId) {
        super("Employee not found with ID: " + employeeId);
    }
}
