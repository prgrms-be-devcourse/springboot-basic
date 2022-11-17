package com.prgrms.springbootbasic.customer.storage;

import com.prgrms.springbootbasic.customer.domain.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("prod")
@Repository
public class JdbcCustomerRepository implements CustomerRepository{

    private static final RowMapper<Customer> ROW_MAPPER = (resultSet, rowNum) -> {
        String name = resultSet.getString("name");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        LocalDateTime createAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new Customer(customerId, name, createAt);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer save(Customer customer) {
        jdbcTemplate.update("insert into customer(customer_id, name, created_at) VALUES(UNHEX(REPLACE(?, '-', '')), ?, ?)",
                customer.getId().toString(),
                customer.getName(),
                Timestamp.valueOf(customer.getCreatedAt()));
        return customer;
    }

    @Override
    public Customer update(Customer customer){
        return null;
    }

    @Override
    public void delete(UUID id){

    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from customer where customer_id = UNHEX(REPLACE(?, '-', ''))",
                ROW_MAPPER,
                id.toString()));
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }
}
