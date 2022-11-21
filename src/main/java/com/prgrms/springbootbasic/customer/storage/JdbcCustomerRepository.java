package com.prgrms.springbootbasic.customer.storage;

import com.prgrms.springbootbasic.common.exception.DataModifyingException;
import com.prgrms.springbootbasic.customer.domain.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Profile("prod | test")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final String INSERT = "INSERT INTO customer(customer_id, name, created_at) VALUES(UNHEX(REPLACE(:customer_id, '-', '')), :name, :created_at)";
    private static final String FIND_ALL = "select * from customer";
    private static final String FIND_BY_ID = "select * from customer where customer_id = UNHEX(REPLACE(:customer_id, '-', ''))";
    private static final String FIND_BY_NAME = "select * from customer where name = :name";
    private static final String UPDATE = "update customer set name = :name where customer_id = UNHEX(REPLACE(:customer_id, '-', ''))";
    private static final String DELETE = "delete from customer where customer_id = UNHEX(REPLACE(:customer_id, '-', ''))";
    private static final String DELETE_BY_NAME = "delete from customer where name = :name";

    private static final String ID = "customer_id";
    private static final String NAME = "name";
    private static final String CREATED_AT = "created_at";

    private static final int ZERO = 0;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> {
        String name = resultSet.getString(NAME);
        UUID customerId = toUUID(resultSet.getBytes(ID));
        LocalDateTime createAt = resultSet.getTimestamp(CREATED_AT).toLocalDateTime();
        return new Customer(customerId, createAt, name);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    @Override
    public void save(Customer customer) {
        Map<String, Object> parameters = Map.of(
                ID, customer.getId().toString(),
                NAME, customer.getName(),
                CREATED_AT, Timestamp.valueOf(customer.getCreatedAt()));
        jdbcTemplate.update(INSERT, parameters);
    }

    @Override
    public void update(Customer customer) {
        Map<String, Object> parameters = Map.of(
                NAME, customer.getName(),
                ID, customer.getId().toString());
        int update = jdbcTemplate.update(UPDATE, parameters);
        if (update == ZERO) {
            throw new DataModifyingException(
                    "Nothing was updated. query: " + UPDATE + " params: " + customer.getName() + ", " + customer.getId());
        }
    }

    @Override
    public void delete(UUID id) {
        int update = jdbcTemplate.update(DELETE, Collections.singletonMap(ID, id.toString()));
        if (update == ZERO) {
            throw new DataModifyingException("Nothing was deleted. query: " + DELETE + " params: " + id);
        }
    }

    @Override
    public void delete(String name) {
        int update = jdbcTemplate.update(DELETE_BY_NAME, Collections.singletonMap(NAME, name));
        if (update == ZERO) {
            throw new DataModifyingException(
                    "Nothing was deleted. query: " + DELETE_BY_NAME + " params: " + name);
        }
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.of(jdbcTemplate.queryForObject(
                FIND_BY_ID,
                Collections.singletonMap(ID, id.toString()),
                ROW_MAPPER));
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return Optional.of(jdbcTemplate.queryForObject(
                FIND_BY_NAME,
                Collections.singletonMap(NAME, name),
                ROW_MAPPER));
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL, ROW_MAPPER);
    }
}