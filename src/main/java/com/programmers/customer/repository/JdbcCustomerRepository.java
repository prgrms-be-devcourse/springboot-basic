package com.programmers.customer.repository;

import com.programmers.customer.domain.Customer;
import com.programmers.exception.NotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("jdbc")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        String sql = "insert into customers(id, name, type) values (?, ?, ?)";

        jdbcTemplate.update(sql,
                customer.getCustomerId().toString(),
                customer.getCustomerName(),
                customer.getCustomerType().toString());

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customers";

        return jdbcTemplate.query(sql, customerRowMapper());
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        String sql = "select * from customers where id = ?";

        try {
            Customer customer = jdbcTemplate.queryForObject(sql, customerRowMapper(), id.toString());
            return Optional.of(customer);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("[ERROR] 회원이 존재하지 않습니다.");
        }
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "update customers set name = ? where id = ?";

        jdbcTemplate.update(sql,
                customer.getCustomerName(),
                customer.getCustomerId().toString());

        return customer;
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "delete from customers where id = ?";

        jdbcTemplate.update(sql, id.toString());
    }

    @Override
    public void deleteAll() {
        String sql = "delete from customers";

        jdbcTemplate.update(sql);
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) ->
                new Customer(UUID.fromString(rs.getString("id")),
                        rs.getString("name"));
    }
}
