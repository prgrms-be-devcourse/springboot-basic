package org.promgrammers.voucher.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.promgrammers.voucher.domain.Customer;
import org.promgrammers.voucher.domain.Voucher;
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

    private static final String FIND_BY_USERNAME = "SELECT * FROM customers WHERE username = :username";

    private static final String FIND_BY_VOUCHER_ID = "SELECT c.* FROM customers c INNER JOIN vouchers v ON c" +
            ".id = v.id WHERE v.id = :id";

    private static final String FIND_ALL = "SELECT * FROM customers";
    private static final String UPDATE = "UPDATE customers SET username = :username, customer_type = :customerType " +
            "WHERE id = :id";
    private static final String DELETE_ALL = "DELETE FROM customers";

    private static final String DELETE_BY_ID = "DELETE FROM customers WHERE id = :id";


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
            //MySQL db에서 UUID 타입을 지원하지 않아 VARCHAR(36)으로 지정했기 때문에 id값을 못찾는 경우 발생
            //String.valueOf를 사용
            Map<String, Object> param = Map.of("id", String.valueOf(customerId));
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

    @Override
    public Optional<Customer> findByUsername(String username) {
        try {
            Map<String, Object> param = Map.of("username", username);
            Customer customer = JdbcTemplate.queryForObject(FIND_BY_USERNAME, param, CustomerUtils.customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            log.error("해당 유저이름을 찾을 수 없습니다. " + username);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByVoucherId(UUID voucherId) {
        try {
            Map<String, Object> param = Map.of("id", String.valueOf(voucherId));
            Customer customer = JdbcTemplate.queryForObject(FIND_BY_VOUCHER_ID, param, CustomerUtils.customerRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            log.error("해당 바우처를 가진 고객을 찾을 수 없습니다. => voucherId: " + voucherId);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID customerId) {
        Map<String, Object> param = Map.of("id", String.valueOf(customerId));
        JdbcTemplate.update(DELETE_BY_ID, param);
    }

    @Override
    public void assignVoucherToCustomer(UUID customerId, Voucher voucher) {
        Optional<Customer> optionalCustomer = findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.addVoucher(voucher);
            update(customer);
        } else {
            log.error("해당 고객을 찾을 수 없습니다. => customerId: {}", customerId);
            throw new IllegalArgumentException("해당 고객을 찾을 수 없습니다.");
        }
    }
}

