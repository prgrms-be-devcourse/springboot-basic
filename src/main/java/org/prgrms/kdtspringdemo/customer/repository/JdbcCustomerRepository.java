package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.model.entity.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private static final String CUSTOMER_ID = "customer_id";
    private static final String NICKNAME = "nickname";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> customerRowMapper = (resultSet, i)
            -> new Customer(toUUID(resultSet.getBytes(CUSTOMER_ID)), resultSet.getString(NICKNAME));

    private Map<String, Object> toParamMap(Customer customer) {
        return Map.of(
                CUSTOMER_ID, uuidToBytes(customer.getId()),
                NICKNAME, customer.getNickname()
        );
    }

    @Override
    public Customer save(Customer customer) {
        jdbcTemplate.update("INSERT INTO customer(customer_id, nickname) VALUES(:customer_id, :nickname)", toParamMap(customer));

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return jdbcTemplate.query("SELECT * FROM customer WHERE customer_id = :customer_id",
                        Collections.singletonMap(CUSTOMER_ID, uuidToBytes(id)),
                        customerRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Customer> findByNickname(String nickname) {
        return jdbcTemplate.query("SELECT * FROM customer WHERE nickname = :nickname",
                        Collections.singletonMap(NICKNAME, nickname),
                        customerRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", customerRowMapper);
    }

    @Override
    public void update(Customer customer) {
        jdbcTemplate.update("UPDATE customer SET nickname = :nickname WHERE customer_id = :customer_id", toParamMap(customer));
    }

    @Override
    public void deleteById(UUID id) {
        jdbcTemplate.update("DELETE FROM customer WHERE customer_id = :customer_id",
                Collections.singletonMap(CUSTOMER_ID, uuidToBytes(id)));
    }
}
