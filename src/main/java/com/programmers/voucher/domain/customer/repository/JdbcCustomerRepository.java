package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.entity.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, customerRowMapper(), customerId.toString()));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByNickname(String nickname) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_NICKNAME_SQL, customerRowMapper(), nickname));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
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

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> Customer.builder()
                .id(UUID.fromString(rs.getString("id")))
                .nickname(rs.getString("nickname"))
                .build();
    }
}
