package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.entity.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final String INSERT_SQL = "INSERT INTO customer(id, nickname) VALUES(?, ?)";
    private static final String FIND_ALL_SQL = "SELECT * FROM customer";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM customer WHERE id = ?";
    private static final String FIND_BY_NICKNAME_SQL = "SELECT * FROM customer WHERE nickname = ?";
    private static final String UPDATE_SQL = "UPDATE customer SET nickname = ? WHERE id = ?";
    private static final String DELETE_SQL = "DELETE FROM customer WHERE id = ?";
    private static final String DELETE_ALL_SQL = "DELETE FROM customer";

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer insert(Customer customer) {
        jdbcTemplate.update(INSERT_SQL, customer.getId().toString(), customer.getNickname());
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, customerRowMapper());
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        return jdbcTemplate.query(FIND_BY_ID_SQL, customerRowMapper(), customerId.toString())
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Customer> findByNickname(String nickname) {
        return jdbcTemplate.query(FIND_BY_NICKNAME_SQL, customerRowMapper(), nickname)
                .stream()
                .findFirst();
    }

    @Override
    public Customer update(Customer customer) {
        jdbcTemplate.update(UPDATE_SQL, customer.getNickname(), customer.getId().toString());
        return customer;
    }

    @Override
    public void delete(UUID customerId) {
        jdbcTemplate.update(DELETE_SQL, customerId.toString());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> new Customer(
                UUID.fromString(rs.getString("id")),
                rs.getString("nickname"));
    }
}
