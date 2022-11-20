package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.exception.ErrorCode;
import org.prgrms.kdt.exception.NotFoundCustomer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    Map<Long, Customer> cache = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Customer insert(String name, String email) {
        String sql = "insert into customers(name, email, created_at) values( :name,:email,:createdAt)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        LocalDateTime createdAt = LocalDateTime.now();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("email", email)
                .addValue("createdAt", createdAt);

        jdbcTemplate.update(sql, param, keyHolder, new String[]{"customer_Id"});
        long customerId = keyHolder.getKey().longValue();
        Customer createdCustomer = new Customer(customerId, name, email, createdAt);
        cache.put(customerId, createdCustomer);
                
        return createdCustomer;
    }

    @Override
    public void update(Long customerId, String name, String email) {
        String sql = "update customers set name = :name, email = :email where customer_id = :customerId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("email", email)
                .addValue("customerId", customerId);
        jdbcTemplate.update(sql, param);

        if (cache.containsKey(customerId)) {
            cache.remove(customerId);
        }

        Customer newCustomer = findById(customerId);
        cache.put(customerId, newCustomer);

    }

    @Override
    public Customer findById(Long customerId) {
        if (cache.containsKey(customerId)) {
            return cache.get(customerId);
        }

        String sql = "select customer_id, name, email, created_at from customers where customer_id = :customerId";
        logger.info("customerId->{}", customerId);

        Map<String, Object> param = Map.of("customerId", customerId);

        try {
            Customer customer = jdbcTemplate.queryForObject(sql, param, customerRowMapper());
            return customer;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundCustomer(ErrorCode.NOT_FOUND_CUSTOMER_EXCEPTION.getMessage());
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select customer_id, name, email, created_at from customers";

        return jdbcTemplate.query(sql, customerRowMapper());
    }


    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customers", Collections.EMPTY_MAP);
        cache.clear();

    }

    private RowMapper<Customer> customerRowMapper() {
        return ((rs, rowNum) -> {
            long customerId = rs.getLong("customer_Id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            LocalDateTime createdAt = rs.getTimestamp("created_At").toLocalDateTime();
            return new Customer(customerId, name, email, createdAt);
        });
    }
}
