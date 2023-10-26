package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Primary
public class CustomerJDBCRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerJDBCRepository.class);
    private static final String FIND_ALL_BLACK_CUSTOMER_QUERY = "SELECT * FROM test.customers WHERE black = TRUE";
    private static final String INSERT_QUERY = "INSERT INTO test.customers(id, name, black) VALUES (UUID_TO_BIN(:id), :name, :black)";
    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        boolean isBlack = resultSet.getBoolean("black");

        return new Customer(id, name, isBlack);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", customer.getCustomerId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("black", customer.isBlack());
        return paramMap;
    }

    @Override
    public List<Customer> findAllBlackCustomer() {
        return jdbcTemplate.query(FIND_ALL_BLACK_CUSTOMER_QUERY, customerRowMapper);
    }

    public void save(Customer customer) {
        int update = jdbcTemplate.update(INSERT_QUERY, toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
    }
}
