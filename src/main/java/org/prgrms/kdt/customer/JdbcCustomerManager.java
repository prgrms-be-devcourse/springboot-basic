package org.prgrms.kdt.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.*;

import static org.prgrms.kdt.customer.utils.CustomerSql.*;

@Repository
public class JdbcCustomerManager implements CustomerManager {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerManager.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Customer> customerMapper = (resultSet, i) -> {
        long customerId = resultSet.getLong("customer_id");
        String name = resultSet.getString("name");

        return new Customer(customerId, name);
    };

    private static Map<String, Object> toParamMap(Customer customer) {
        return Map.of(
                "customerId", customer.getId(),
                "name", customer.getName()
        );
    }

    public JdbcCustomerManager(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName("customers")
                .usingGeneratedKeyColumns("customer_id");
    }

    @Override
    public Customer save(Customer customer) {
        long generatedId = simpleJdbcInsert.executeAndReturnKey(toParamMap(customer)).longValue();
        return new Customer(generatedId, customer.getName());
    }

    @Override
    public List<Customer> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL.getSql(), customerMapper);
    }

    @Override
    public Optional<Customer> findById(long customerId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(FIND_BY_ID.getSql(),
                    Collections.singletonMap("customerId", customerId),
                    customerMapper
            ));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Customer customer) {
        int update = namedParameterJdbcTemplate.update(UPDATE.getSql(),
                toParamMap(customer)
        );
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was updated");
        }
    }

    @Override
    public void deleteById(long customerId) {
        int update = namedParameterJdbcTemplate.update(DELETE_BY_ID.getSql(),
                Collections.singletonMap("customerId", customerId)
        );
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was deleted");
        }
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update(DELETE_ALL.getSql(), Collections.emptyMap());
    }


}
