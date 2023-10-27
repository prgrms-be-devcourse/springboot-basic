package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.customer.domain.CustomerType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("default")
public class JdbcCustomerRepository implements CustomerRepository {

    private static final String READ = "SELECT * FROM customer WHERE customer_type = 'BLACK'";

    private static final RowMapper<Customer> customerRowMapper = (resultSet, index) -> {

        UUID customerId = bytesToUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        CustomerType customerType = CustomerType.valueOf(resultSet.getString("customer_type"));

        return new Customer(customerId, name, customerType);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAllBlack() {

        List<Customer> customers = jdbcTemplate.query(READ, customerRowMapper);

        return customers;
    }

    private static UUID bytesToUUID(byte[] bytes) {

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        long mostSignificantBits = byteBuffer.getLong();
        long leastSignificantBits = byteBuffer.getLong();

        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}
