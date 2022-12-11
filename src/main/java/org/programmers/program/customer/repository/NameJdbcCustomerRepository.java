package org.programmers.program.customer.repository;

import org.programmers.program.customer.model.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class NameJdbcCustomerRepository implements CustomerRepository{
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public NameJdbcCustomerRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    static UUID toUUID(byte[]  bytes){
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var email = resultSet.getString("email");
        var name = resultSet.getString("name");

        return new Customer(customerId, email, name);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>(){{
            put("customerId", customer.getId().toString().getBytes());
            put("email", customer.getEmail());
            put("name", customer.getName());
            put("isBlackConsumer", customer.getIsBlackConsumer());
            put("createdAt", Timestamp.valueOf(customer.getCreatedAt()));
            put("lastModifiedAt", Timestamp.valueOf(customer.getLastModifiedAt()));
        }};
    }

    @Override
    public Customer insert(Customer customer) {
        var updateState = namedJdbcTemplate.update(
                "insert into customers(customer_id, email, name, is_black_consumer, created_at, last_modified_at) values " +
                        " (UUID_TO_BIN(:customerId), :email, :name, :isBlackConsumer, :createdAt, :lastModifiedAt)",
                toParamMap(customer)
        );
        if (updateState != 1)
            throw new RuntimeException("Failed to insert");
        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        try {
            return Optional.ofNullable(
                    namedJdbcTemplate.queryForObject(
                            "select * from customers where customer_id = UUID_TO_BIN(:customerId)",
                            Collections.singletonMap("customerId", id.toString().getBytes()),
                            customerRowMapper
                    )
            );
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByName(String name) {
        try {
            return Optional.ofNullable(
                    namedJdbcTemplate.queryForObject(
                            "select * from customers where name = :name",
                            Collections.singletonMap("name", name),
                            customerRowMapper
                    )
            );
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    namedJdbcTemplate.queryForObject(
                            "select * from customers where email = :email",
                            Collections.singletonMap("email", email),
                            customerRowMapper
                    )
            );
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return namedJdbcTemplate.query("select * from customers;", customerRowMapper);

    }

    @Override
    public int update(Customer customer) {
        var update = namedJdbcTemplate.update(
                "update customers set email = :email, name = :name, is_black_consumer = :isBlackConsumer " +
                        "where customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer)
        );
        if(update != 1)
            return -1;
        return 1;
    }

    @Override
    public void deleteAll() {
        namedJdbcTemplate.update("delete from customers", Collections.emptyMap());

    }

    @Override
    public int count() {
        return namedJdbcTemplate.queryForObject("select count(*) from customers", Collections.emptyMap(),Integer.class);

    }
}
