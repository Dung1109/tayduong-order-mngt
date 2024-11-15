package tayduong.org.tayduong_order_system.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.time.LocalDate;
import java.util.UUID;

public class OrderIdGenerator implements IdentifierGenerator {
    
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        LocalDate now = LocalDate.now();
        String year = String.valueOf(now.getYear());
        String uniqueId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return String.format("ORD-%s-%s", year, uniqueId);
    }
}
