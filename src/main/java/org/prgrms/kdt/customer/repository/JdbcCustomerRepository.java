package org.prgrms.kdt.customer.repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.customer.model.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));

        return new Customer(customerId, name, createdAt);
    };

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        int update = jdbcTemplate.update(
            "insert into customers(customer_id, name, created_at)"
                + " values(UUID_TO_BIN(:customerId), :name, :createdAt)"
            , toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "select * from customers where customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers", Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId());
        paramMap.put("name", customer.getName());
        paramMap.put("createdAt", customer.getCreatedAt());

        return paramMap;
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

}
