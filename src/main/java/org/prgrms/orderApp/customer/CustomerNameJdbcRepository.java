package org.prgrms.orderApp.customer;

import org.prgrms.orderApp.util.library.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile("local")
public class CustomerNameJdbcRepository implements CustomerRepository{
    private static final Logger logger = LoggerFactory.getLogger(CustomerNameJdbcRepository.class);

    private final String SELECT_SQL = "select * from customers WHERE name=?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String SELECT_BY_NAME_SQL = "select * from customers where name=:name";
    private final String SELECT_BY_ID_SQL = "select * from customers where customer_id=UUID_TO_BIN(:customerId)";
    private final String SELECT_BY_EMAIL = "select * from customers where email=:email";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email, created_at) value (UUID_TO_BIN(:customerId),:name,:email,:createdAt)";
    private final String UPDATE_BY_ID_SQL = "update customers set name =:name, last_login_at=:lastLoginAt where customer_id=UUID_TO_BIN(:customerId)";
    private final String DELETE_ALL_SQL = "delete from customers";
    private final String COUNT_SQL = "select count(*) from customer";

    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;


    private Map<String, Object> toParameter(Customer customer) {
        return new HashMap<>() {{
            put("customerId", customer.getCustomerId().toString().getBytes(StandardCharsets.UTF_8));
            put("name", customer.getName());
            put("email", customer.getEmail());
            put("createdAt", customer.getCreatedAt());
            put("lastLoginAt", customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);

        }};
    }
    public CustomerNameJdbcRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate){
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, (resultSet, i)-> mapToCustomerByJdbcTemplate(resultSet));
    }


    @Override
    public Customer insert(Customer customer) {
        var insert = jdbcTemplate.update(INSERT_SQL, toParameter(customer));
        if (insert != 1) throw new RuntimeException("Nothing was inserted");
        return customer;

    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update(UPDATE_BY_ID_SQL, toParameter(customer));
        if (update != 1) throw new RuntimeException("Nothing was updated");
        return customer;

    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,Collections.singletonMap("customerId", customerId.toString().getBytes()), (resultSet, i) -> mapToCustomerByJdbcTemplate(resultSet)));
        } catch (EmptyResultDataAccessException e){
            logger.error("Got empty message: " + e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_NAME_SQL, Collections.singletonMap("name",name), (resultSet, i) -> mapToCustomerByJdbcTemplate(resultSet)));
        } catch (EmptyResultDataAccessException e){
            logger.error("Got empty message: " + e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_EMAIL, Collections.singletonMap("email",email),(resultSet, i) -> mapToCustomerByJdbcTemplate(resultSet)));
        } catch (EmptyResultDataAccessException e){
            logger.error("Got empty message: " + e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Collections.emptyMap(), Integer.class);
    }

    private Customer mapToCustomerByJdbcTemplate(ResultSet resultSet) throws SQLException {
        var customerName = resultSet.getString("name");
        var customerId = Common.toUUID(resultSet.getBytes("customer_id"));
        var email = resultSet.getString("email");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime(): null;
        logger.info("customer id -> {}, name -> {}, created at -> {}", customerId, customerName, createdAt);
        return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
    }

}
