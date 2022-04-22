package org.prgrms.voucherapp.engine.customer.repository;

import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.exception.SqlStatementFailException;
import org.prgrms.voucherapp.global.Util;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static RowMapper<Customer> customerRowMapper = (resultSet, rowNum) -> {
        var customerId = Util.toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        var status = resultSet.getString("status");
        return new Customer(customerId, name, email, status);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<String, Object>() {{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("status", customer.getStatus());
        }};
    }

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static RowMapper<Customer> getCustomerRowMapper() {
        return customerRowMapper;
    }

    @Override
    public Customer insert(Customer customer) {
        int insert = jdbcTemplate.update("insert into customers(customer_id, name, email, status) values(UUID_TO_BIN(:customerId), :name, :email, :status)", toParamMap(customer));
        if (insert != 1) throw new SqlStatementFailException("정상적으로 삽입되지 않았습니다.");
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        int update = jdbcTemplate.update("update customers set name = :name, status = :status where customer_id = UUID_TO_BIN(:customerId)", toParamMap(customer));
        if (update != 1) throw new SqlStatementFailException("정상적으로 수정되지 않았습니다.");
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customer_id = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID customerId) {
        int delete = jdbcTemplate.update("delete from customers where customer_id = UUID_TO_BIN(:customerId)",Collections.singletonMap("customerId", customerId.toString().getBytes()));
        if (delete != 1) throw new SqlStatementFailException("정상적으로 삭제되지 않았습니다.");
    }
}
