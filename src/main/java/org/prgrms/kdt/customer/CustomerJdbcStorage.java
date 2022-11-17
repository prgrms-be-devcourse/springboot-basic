package org.prgrms.kdt.customer;

import org.prgrms.kdt.exceptions.CustomerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerJdbcStorage implements CustomerStorage{

    private static final Logger logger = LoggerFactory.getLogger(CustomerException.class);

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        UUID customerId = UUID.fromString(resultSet.getString("customer_id"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, customerName, email, createdAt);
    };

    @Override
    public void insert(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customer(customer_id, name, email, created_at) VALUES (?, ?, ?, ?)",
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                Timestamp.valueOf(customer.getCreateAt()));
        if(update != 1) {
            throw new CustomerException("고객 저장에 실패했습니다.");
        }
    }

    @Override
    public Optional<Customer> findById(String customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("select * from customer WHERE customer_id = ?", customerRowMapper, customerId));
        } catch (EmptyResultDataAccessException e){
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customer", customerRowMapper);
    }

    @Override
    public void deleteCustomerById(String customerId) {
        jdbcTemplate.update("DELETE FROM customer WHERE customer_id = ?", customerId);
    }

    @Override
    public void update(Customer customer) {
        int update = jdbcTemplate.update("UPDATE customer SET name = ? WHERE customer_id = ?",
                customer.getName(),
                customer.getCustomerId());
        if(update != 1) {
            throw new RuntimeException("고객 정보를 업데이트 하는 것에 실패하였습니다.");
        }
    }
}
