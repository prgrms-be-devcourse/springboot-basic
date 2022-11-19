package org.prgrms.kdt.customer;

import org.prgrms.kdt.customer.utils.CustomerSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.prgrms.kdt.customer.utils.CustomerSql.*;

@Repository
public class JdbcCustomerManager implements CustomerManager {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerManager.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcCustomerManager(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("customers")
                .usingGeneratedKeyColumns("customer_id");
    }

    @Override
    public Customer save(Customer customer) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", customer.getName());
        long generatedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Customer(generatedId, customer.getName());
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL.getSql(), customerMapper);
    }

    @Override
    public Optional<Customer> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID.getSql(),
                    customerMapper,
                    id
            ));
        } catch (EmptyResultDataAccessException exception) {
            logger.error("Got Empty Result", exception);
            return Optional.empty();
        }
    }

    @Override
    public void update(Customer customer) {
        int update = jdbcTemplate.update(UPDATE.getSql(),
                customer.getName(),
                customer.getId()
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_BY_ID.getSql(),
                id
        );
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL.getSql());
    }

    private static final RowMapper<Customer> customerMapper = (resultSet, i) -> {
        long customerId = resultSet.getLong("customer_id");
        String name = resultSet.getString("name");

        return new Customer(customerId, name);
    };
}
