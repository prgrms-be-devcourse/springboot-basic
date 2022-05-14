package com.dojinyou.devcourse.voucherapplication.customer;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import com.dojinyou.devcourse.voucherapplication.customer.domain.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Customer> customerRowMapper = (rs, i) -> Customer.from(rs);

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer create(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int updateRow = jdbcTemplate.update("INSERT INTO customer(name) VALUES(:name)",
                                            new MapSqlParameterSource().addValue("name", customer.getName()),
                                            keyHolder);
        if (updateRow != 1) {
            throw new RuntimeException();
        }

        var firstValue = keyHolder.getKeyList().get(0).values().stream().findFirst();
        long newId = 0;
        if (firstValue.isPresent() && firstValue.get() instanceof BigInteger) {
            newId = ((BigInteger) firstValue.get()).longValue();
        } else if (firstValue.isPresent() && firstValue.get() instanceof Long) {
            newId = (long) firstValue.get();
        } else {
            throw new RuntimeException();
        }

        return findById(newId).orElseThrow(RuntimeException::new);
    }

    @Override
    public Optional<Customer> findById(long id) {
        List<Customer> foundCustomer = jdbcTemplate.query("SELECT * FROM customer WHERE id = :id",
                                                          new MapSqlParameterSource().addValue("id", id),
                                                          customerRowMapper);
        if (foundCustomer.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(foundCustomer.get(0));
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", new MapSqlParameterSource(),
                                  customerRowMapper);
    }

    @Override
    public void deleteById(long id) {
        int updateRow = jdbcTemplate.update("DELETE FROM customer WHERE id = :id",
                                            new MapSqlParameterSource().addValue("id", id));
        if (updateRow < 1) {
            throw new NotFoundException();
        }
        if (updateRow > 1) {
            throw new RuntimeException();
        }
    }
}

