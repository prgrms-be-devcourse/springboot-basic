package org.prgrms.memory;

import static org.prgrms.memory.query.CustomerSQL.DELETE_ALL;
import static org.prgrms.memory.query.CustomerSQL.DELETE_BY_ID;
import static org.prgrms.memory.query.CustomerSQL.FIND_ALL;
import static org.prgrms.memory.query.CustomerSQL.FIND_BY_ID;
import static org.prgrms.memory.query.CustomerSQL.INSERT;
import static org.prgrms.memory.query.CustomerSQL.UPDATE;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Profile("jdbc")
@Repository
public class CustomerDBMemory {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final int NO_RESULT = 0;

    public CustomerDBMemory(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID id = UUID.fromString(resultSet.getString("id"));
        String name = resultSet.getString("name");

        return new Customer(id, name);
    };

    public Customer save(Customer customer) {
        Map<String, String> paramMap = Map.of(
            "id", String.valueOf(customer.getId()),
            "name", customer.getName());
        try {
            jdbcTemplate.update(INSERT.getSql(), paramMap);
        } catch (BadSqlGrammarException e) {
            throw new DataAccessException(
                "please check id *current id: " + customer.getId(), e) {
            };
        }
        return new Customer(customer.getId(), customer.getName());
    }

    public Optional<Customer> findById(UUID id) {

        try {
            Customer customer = jdbcTemplate.queryForObject(FIND_BY_ID.getSql(),
                Collections.singletonMap("id", String.valueOf(id)), customerRowMapper);
            return Optional.of(customer);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL.getSql(), customerRowMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL.getSql(), Collections.emptyMap());
    }

    public void deleteById(UUID id) {
        jdbcTemplate.update(DELETE_BY_ID.getSql(),
            Collections.singletonMap("id", String.valueOf(id)));

    }

    public Customer update(Customer customer) {
        Map<String, String> paramMap = Map.of(
            "name", customer.getName(),
            "id", String.valueOf(customer.getId()));
        int updateNum = jdbcTemplate.update(UPDATE.getSql(), paramMap);
        if (updateNum == NO_RESULT) {
            throw new NoSuchElementException(
                "That ID could not be found *current ID : " + customer.getId());
        }
        return customer;
    }
}
