package com.zerozae.voucher.repository.customer;

import com.zerozae.voucher.domain.customer.Customer;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.zerozae.voucher.util.UuidConverter.toUUID;


@Profile("prod")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final String CUSTOMER_ID = "customerId";
    private static final String CUSTOMER_NAME = "customerName";
    private static final String CUSTOMER_TYPE = "customerType";
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
    @Transactional
    public Customer save(Customer customer) {
        String sql = "insert into customers(customer_id,customer_name , customer_type) values (UUID_TO_BIN(:customerId), :customerName, :customerType)";
        int updated = jdbcTemplate.update(
                sql,
                toParamMap(customer));

        if(updated != 1) {
            String SAVE_FAILED_MESSAGE = "저장에 실패했습니다.";
            throw ErrorMessage.error(SAVE_FAILED_MESSAGE);
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
                    Collections.singletonMap(CUSTOMER_ID, customerId.toString().getBytes()),
                    customerRowMapper);
            return Optional.of(customer);
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public void deleteById(UUID customerId) {
        String sql = "delete from customers where customer_id = UUID_TO_BIN(:customerId)";
        jdbcTemplate.update(
                sql,
                Collections.singletonMap(CUSTOMER_ID, customerId.toString().getBytes()));
    }

    @Transactional
    @Override
    public void deleteAll() {
        String sql = "delete from customers";
        jdbcTemplate.getJdbcOperations().update(sql);
    }

    @Transactional
    @Override
    public void update(UUID customerId, CustomerRequest customerRequest) {
        String sql = "update customers set customer_name = :customerName, customer_type = :customerType where customer_id = UUID_TO_BIN(:customerId)";
        int update = jdbcTemplate.update(
                sql,
                Map.of(
                        CUSTOMER_NAME, customerRequest.getCustomerName(),
                        CUSTOMER_TYPE, customerRequest.getCustomerType().toString(),
                        CUSTOMER_ID, customerId.toString().getBytes()));

        if(update != 1) {
            String UPDATE_FAILED_MESSAGE = "업데이트에 실패했습니다.";
            throw ErrorMessage.error(UPDATE_FAILED_MESSAGE);
        }
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>() {{
            put(CUSTOMER_ID, customer.getCustomerId().toString().getBytes());
            put(CUSTOMER_NAME, customer.getCustomerName());
            put(CUSTOMER_TYPE, customer.getCustomerType().toString());
        }};
    }
}