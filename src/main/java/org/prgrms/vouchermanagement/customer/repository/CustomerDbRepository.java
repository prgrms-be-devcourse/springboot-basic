package org.prgrms.vouchermanagement.customer.repository;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerDbRepository implements CustomerRepository{

    private final String LOAD = "SELECT customer_id, customer_name, customer_age FROM customer";

    private final Map<UUID, Customer> storage = new ConcurrentHashMap<>();
    private final JdbcTemplate jdbcTemplate;

    public CustomerDbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> showCustomerList() {
        load();

        return storage.values().stream()
                .toList();
    }

    private void load() {
        List<Customer> customers = jdbcTemplate.query(LOAD, new CustomerDbRepository.CustomerRowMapper());
        for (Customer customer : customers) {
            storage.put(customer.getUserId(), customer);
        }
    }

    private class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            UUID customerId = toUUID(resultSet.getBytes("customer_id"));
            String customerName = resultSet.getString("customer_name");
            int customerAge = resultSet.getInt("customer_age");

            return new Customer(customerId, customerName, customerAge);
        }
    }

    private UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }
}
