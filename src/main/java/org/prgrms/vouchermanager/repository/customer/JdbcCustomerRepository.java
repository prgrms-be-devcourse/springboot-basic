package org.prgrms.vouchermanager.repository.customer;

import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Profile("jdbc")
public class JdbcCustomerRepository implements CustomerRepositroy{
    private final String INSERT_VOUCHER = "INSERT INTO customer(id, name, email, isBlack) VALUES(UUID_TO_BIN(?), ?, ?, ?)";
    private final String SELECT_BY_ID = "select * from voucher where id = UUID_TO_BIN(?)";
    private final String SELECT_ALL = "select * from voucher";
    private final String DELETE_ALL = "delete from voucher";
    private final String DELETE_BY_ID = "delete from voucher where id = UUID_TO_BIN(?)";
    private final JdbcTemplate jdbcTemplate;
    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
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

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> deleteById(UUID customerId) {
        return null;
    }


    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
