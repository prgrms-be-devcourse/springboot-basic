package org.prgrms.vouchermanager.repository.customer;

import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepositroy{
    private JdbcTemplate jdbcTemplate;
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerName = resultSet.getString("name");
        var email = resultSet.getString("email");
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        boolean isBlack = resultSet.getBoolean("isBlack");
        return new Customer(customerId, customerName, email, isBlack);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("isBlack", customer.getIsBlack());
        }};
    }
    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }


    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
