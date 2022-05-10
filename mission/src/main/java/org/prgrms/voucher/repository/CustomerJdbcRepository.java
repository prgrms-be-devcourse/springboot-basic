package org.prgrms.voucher.repository;

import org.prgrms.voucher.models.Customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {

        if (customer == null) {
            throw new IllegalArgumentException("Customer is null");
        }

        if (customer.getCustomerId() == null) {
            int update = jdbcTemplate.update("INSERT INTO customer(customer_id, customer_name, created_at, updated_at)" +
                    " VALUES(:customerId, :customerName, :createdAt, :updatedAt)", toParamMap(customer));

            if (update != 1) {
                throw new RuntimeException("Nothing was inserted");
            }

            return customer;
        }

        throw new IllegalArgumentException("Wrong Customer..");
    }

    @Override
    public List<Customer> findAll() {

        return jdbcTemplate.query("select * from customer", customerRowMapper);
    }

    @Override
    public List<Customer> findByTerm(LocalDate after, LocalDate before) {

        return jdbcTemplate.query("select * from customer WHERE DATE(created_at) BETWEEN :after AND :before ", toParamMapByTerm(after, before), customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(Long customerId) {

        return jdbcTemplate.query("SELECT * FROM customer WHERE customer_id = :customerId",
                Collections.singletonMap("customerId", customerId.toString()), customerRowMapper).stream().findAny();
    }

    @Override
    public void deleteById(Long customerId) {

        int update = jdbcTemplate.update("DELETE FROM customer WHERE customer_id = :customerId" , Collections.singletonMap("customerId", customerId.toString()));

        if (update != 1) {
            throw new IllegalStateException();
        }
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {

        Long customerId = resultSet.getLong("customer_id");
        String customerName = resultSet.getString("customer_name");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Customer(customerId, customerName, createdAt, updatedAt);
    };

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {

        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    private Map<String, Object> toParamMap(Customer customer) {

        var paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customer.getCustomerId());
        paramMap.put("customerName", customer.getCustomerName());
        paramMap.put("createdAt", customer.getCreatedAt());
        paramMap.put("updatedAt", customer.getUpdatedAt());

        return paramMap;
    }

    private Map<String, Object> toParamMapByTerm(LocalDate after, LocalDate before) {

        var paramMap = new HashMap<String, Object>();
        paramMap.put("after", after);
        paramMap.put("before", before);

        return paramMap;
    }
}