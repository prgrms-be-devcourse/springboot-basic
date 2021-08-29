package org.prgrms.kdt.devcourse.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository{
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String,Object> toParamMap(Customer customer) {
        return new HashMap<String, Object>(){{
            put("customerId", customer.getCustomerId().toString().getBytes());
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", customer.getCreatedAt());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
        }};
    }

    private RowMapper<Customer> customerRowMapper= ((resultSet, i) -> {
        UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String customerEmail = resultSet.getString("email");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        return new Customer(customerId, customerName, customerEmail, lastLoginAt, createdAt);
    });


    @Override
    public Customer insert(Customer customer) {
        int insertResult = jdbcTemplate.update("insert into customers(customer_id,name,email,created_at) values (UUID_TO_BIN(:customerId),:name,:email,:createdAt)", toParamMap(customer));

        if(insertResult!=1){
            throw new RuntimeException("Noting was inserted");
        }
        return customer;
    }



    @Override
    public Customer update(Customer customer) {
        int updateResult = jdbcTemplate.update("update customers set name = :name,email = :email,last_login_at = :lastLoginAt where customer_id=UUID_TO_BIN(:customerId)",toParamMap(customer));
        if(updateResult!=1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("select * from customers",customerRowMapper);
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where customerId = UUID_TO_BIN(:customerId)",
                    Collections.singletonMap("customerId",customerId.toString().getBytes()),
                    customerRowMapper));
        }catch (EmptyStackException emptyStackException){
            return Optional.empty();
        }

    }

    @Override
    public Optional<Customer> findByName(String customerName) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where name = :name",
                    Collections.singletonMap("name",customerName),
                    customerRowMapper));
        }catch (EmptyStackException emptyStackException){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByIEmail(String customerEmail) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from customers where email = :email",
                    Collections.singletonMap("email",customerEmail),
                    customerRowMapper));
        }catch (EmptyStackException emptyStackException){
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("delete from customers");
    }
}
