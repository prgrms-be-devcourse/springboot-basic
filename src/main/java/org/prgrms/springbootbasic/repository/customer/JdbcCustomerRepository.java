package org.prgrms.springbootbasic.repository.customer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final JdbcTemplate jdbcTemplate;

    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email) values(UUID_TO_BIN(?), ?, ?)";
    private final String DELETE_ALL_SQL = "delete from customers";
    private final String UPDATE_BY_ID_SQL = "update customers set name = ? where customer_id = UUID_TO_BIN(?)";
    private final String SELECT_BY_EMAIL = "select * from customers where email = ?";

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, (resultSet, i) -> {
            var customerId = toUUID(resultSet.getBytes("customer_id"));
            var name = resultSet.getString("name");
            var email = resultSet.getString("email");
            return new Customer(customerId, name, email);
        });
    }

    public UUID save(Customer customer) {
        var insert = jdbcTemplate.update(INSERT_SQL,
            customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8),
            customer.getName(),
            customer.getEmail());
        if (insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customer.getCustomerId();
    }

    public void removeAll() {
        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    public void changeName(UUID customerId, String name) {
        var update = jdbcTemplate.update(UPDATE_BY_ID_SQL,
            name,
            customerId.toString().getBytes(StandardCharsets.UTF_8));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
    }

    public List<Customer> findByEmail(String email) {
        return jdbcTemplate.query(SELECT_BY_EMAIL, (resultSet, i) -> {
            var customerId = toUUID(resultSet.getBytes("customer_id"));
            var name = resultSet.getString("name");
            var CustomerEmail = resultSet.getString("email");
            return new Customer(customerId, name, CustomerEmail);
        }, email);
    }
}
