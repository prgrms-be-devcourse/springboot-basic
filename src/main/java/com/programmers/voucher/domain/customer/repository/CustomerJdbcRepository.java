package com.programmers.voucher.domain.customer.repository;

import com.programmers.voucher.domain.customer.domain.Customer;
import com.programmers.voucher.domain.customer.dto.CustomerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.global.util.DataAccessConstants.UPDATE_ONE;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final NamedParameterJdbcTemplate template;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate template) {
        this.template =template;
    }

    @Override
    public void save(Customer customer) {
        CustomerDto customerDto = customer.toDto();

        String sql = "insert into customer(customer_id, email, name, banned)" +
                " values(:customerId, :email, :name, :banned)";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerDto.getCustomerId())
                .addValue("email", customerDto.getEmail())
                .addValue("name", customerDto.getName())
                .addValue("banned", customerDto.isBanned());

        int saved = template.update(sql, param);
        if (saved != UPDATE_ONE) {
            DataAccessException ex = new IncorrectResultSizeDataAccessException(UPDATE_ONE, saved);
            LOG.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        String sql = "select * from customer where customer_id = :customerId";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId.toString());

        try {
            Customer customer = template.queryForObject(sql, param, customerRowMapper());
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "select * from customer where email = :email";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("email", email);

        try {
            Customer customer = template.queryForObject(sql, param, customerRowMapper());
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customer";
        return template.query(sql, Map.of(), customerRowMapper());
    }

    @Override
    public List<Customer> findAllByBanned() {
        String sql = "select * from customer where banned = true";
        return template.query(sql, customerRowMapper());
    }

    @Override
    public void update(Customer customer) {
        CustomerDto customerDto = customer.toDto();

        String sql = "update customer set name = :name, banned = :banned where customer_id = :customerId";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", customerDto.getName())
                .addValue("customerId", customerDto.getCustomerId())
                .addValue("banned", customerDto.isBanned());

        int updated = template.update(sql, param);
        if (updated != UPDATE_ONE) {
            DataAccessException exception = new IncorrectResultSizeDataAccessException(UPDATE_ONE, updated);
            LOG.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Override
    public void deleteById(UUID customerId) {
        String sql = "delete from customer where customer_id = :customerId";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerId);
        int deleted = template.update(sql, param);
        if (deleted != UPDATE_ONE) {
            DataAccessException exception = new IncorrectResultSizeDataAccessException(UPDATE_ONE, deleted);
            LOG.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUID.fromString(rs.getString("customer_id"));
            String email = rs.getString("email");
            String name = rs.getString("name");
            boolean banned = rs.getBoolean("banned");

            Customer customer = new Customer(customerId, email, name);
            customer.update(name, banned);

            return customer;
        };
    }
}
