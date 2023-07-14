package org.promgrammers.voucher.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.promgrammers.voucher.domain.Customer;
import org.promgrammers.voucher.util.CustomerUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JdbcCustomerRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate JdbcTemplate;


    private static final String SAVE = "INSERT INTO customers(id, username, customer_type) VALUES(:id, :username, " +
            ":customerType)";
    private static final String FIND_BY_ID = "SELECT * FROM customers WHERE id = :id";
    private static final String FIND_ALL = "SELECT * FROM customers";
    private static final String UPDATE = "UPDATE customers SET username = :username, customer_type = :customerType " +
            "WHERE id = :id";
    private static final String DELETE_ALL = "DELETE FROM customers";

    @Override
    public Customer save(Customer customer) {
        SqlParameterSource parameterSource = CustomerUtils.createParameterSource(customer);
        int update = JdbcTemplate.update(SAVE, parameterSource);

        if (update != 1) {
            throw new DataAccessException("이미 존재하는 회원입니다. " + customer.getId()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            Map<String, Object> param = Map.of("customerId", customerId);
            Customer customer = JdbcTemplate.queryForObject(FIND_BY_ID, param, CustomerUtils.customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            log.error("해당 고객이 존재하지 않습니다. " + customerId);
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return JdbcTemplate.query(FIND_ALL, CustomerUtils.customerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        JdbcTemplate.update(UPDATE, CustomerUtils.createParameterSource(customer));
        return customer;
    }

    @Override
    public void deleteAll() {
        JdbcTemplate.update(DELETE_ALL, Collections.emptyMap());

    }
}
