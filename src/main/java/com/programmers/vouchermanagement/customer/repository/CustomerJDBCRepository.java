package com.programmers.vouchermanagement.customer.repository;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.util.DomainMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Repository
@Primary
public class CustomerJDBCRepository implements CustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(CustomerJDBCRepository.class);
    private static final String FIND_ALL_BLACK_CUSTOMER_QUERY = "SELECT * FROM test.customers WHERE black = TRUE";
    private static final String INSERT_QUERY = "INSERT INTO test.customers(id, name, black) VALUES (UUID_TO_BIN(:id), :name, :black)";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DomainMapper domainMapper;

    public CustomerJDBCRepository(NamedParameterJdbcTemplate jdbcTemplate, DomainMapper domainMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.domainMapper = domainMapper;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }


    @Override
    public List<Customer> findAllBlackCustomer() {
        return jdbcTemplate.query(FIND_ALL_BLACK_CUSTOMER_QUERY, domainMapper.customerRowMapper);
    }

    public void save(Customer customer) {
        int update = jdbcTemplate.update(INSERT_QUERY, domainMapper.customerToParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
    }
}
