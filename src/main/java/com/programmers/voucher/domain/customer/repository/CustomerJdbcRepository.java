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

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.global.util.DataErrorMessages.INCORRECT_UPDATED_RESULT_SIZE;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final NamedParameterJdbcTemplate template;

    public CustomerJdbcRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Customer customer) {
        CustomerDto customerDto = customer.toDto();

        String sql = "insert into customer(customer_id, name) values(:customerId, :name)";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("customerId", customerDto.getCustomerId())
                .addValue("name", customerDto.getName());

        int saved = template.update(sql, param);
        if (saved != 1) {
            DataAccessException exception
                    = new IncorrectResultSizeDataAccessException(INCORRECT_UPDATED_RESULT_SIZE, 1, saved);
            LOG.error(exception.getMessage(), exception);
            throw exception;
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

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUID.fromString(rs.getString("customer_id"));
            String name = rs.getString("name");

            return new Customer(customerId, name);
        };
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteById(UUID customerId) {

    }
}
