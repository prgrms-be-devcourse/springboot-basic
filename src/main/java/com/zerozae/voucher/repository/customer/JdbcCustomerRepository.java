package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.zerozae.voucher.util.UuidConverter.toUUID;


@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("customer_name");
        CustomerType customerType = CustomerType.valueOf(resultSet.getString("customer_type"));

        return new Customer(customerId, customerName, customerType);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customers(customer_id,customer_name , customer_type) values (UUID_TO_BIN(:customerId), :customerName, :customerType)";
        int updated = jdbcTemplate.update(
                sql,
                toParamMap(customer));

        if(updated != 1) {
            throw ExceptionMessage.error("저장에 실패했습니다.");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customers where customer_id = UUID_TO_BIN(:customerId)";
        try {
            Customer customer = jdbcTemplate.queryForObject(
                    sql,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper);
            return Optional.of(customer);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(UUID customerId) {
        String sql = "delete from customers where customer_id = UUID_TO_BIN(:customerId)";
        jdbcTemplate.update(
                sql,
                Collections.singletonMap("customerId", customerId.toString().getBytes()));
    }

    @Override
    public void deleteAll() {
        String sql = "delete from customers";
        jdbcTemplate.getJdbcOperations().update(sql);
    }

    @Override
    public void update(UUID customerId, CustomerUpdateRequest customerRequest) {
        String sql = "update customers set customer_name = :customerName, customer_type = :customerType where customer_id = UUID_TO_BIN(:customerId)";
        int update = jdbcTemplate.update(
                sql,
                Map.of(
                        "customerName", customerRequest.getCustomerName(),
                        "customerType", customerRequest.getCustomerType().toString(),
                        "customerId", customerId.toString().getBytes()));

        if(update != 1) {
            throw ExceptionMessage.error("업데이트에 실패했습니다.");
        }
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("customerName", customer.getCustomerName());
            put("customerType", customer.getCustomerType().toString());
        }};
    }
}
