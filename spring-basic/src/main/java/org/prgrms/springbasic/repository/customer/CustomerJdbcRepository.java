package org.prgrms.springbasic.repository.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.domain.customer.CustomerType;
import org.prgrms.springbasic.utils.exception.NoDatabaseChangeException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_INSERTED;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_UPDATED;
import static org.prgrms.springbasic.utils.sql.CustomerSQL.*;

@Slf4j
@Profile("prd")
@Repository
@RequiredArgsConstructor
public class CustomerJdbcRepository implements CustomerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Customer save(Customer customer) {
        var insertedCount = jdbcTemplate.update(CREATE_CUSTOMER.getQuery(), toParamMap(customer));

        if(insertedCount != 1) {
            log.error("Got not inserted result: {}", customer);

            throw new NoDatabaseChangeException(NOT_INSERTED.getMessage());
        }

        return customer;
    }

    @Override
    public Optional<Customer> findByCustomerId(UUID customerId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_CUSTOMER_ID.getQuery(),
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_VOUCHER_ID.getQuery(),
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    customerRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findCustomers() {
        return jdbcTemplate.query(SELECT_CUSTOMERS.getQuery(), customerRowMapper);
    }

    @Override
    public int countCustomers() {
        var count = jdbcTemplate.queryForObject(SELECT_COUNT.getQuery(),
                Collections.emptyMap(),
                Integer.class);

        return (count == null) ? 0 : count;
    }

    @Override
    public Customer update(Customer customer) {
        var updatedCount = jdbcTemplate.update(UPDATE_CUSTOMER.getQuery(), toParamMap(customer));

        if(updatedCount != 1) {
            log.error("Got not updated result: {}", customer);

            throw new NoDatabaseChangeException(NOT_UPDATED.getMessage());
        }

        return customer;
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        jdbcTemplate.update(DELETE_BY_CUSTOMER_ID.getQuery(), Collections.singletonMap("customerId", customerId.toString().getBytes()));
    }

    @Override
    public void deleteCustomers() {
        jdbcTemplate.update(DELETE_CUSTOMERS.getQuery(), Collections.emptyMap());
    }

    private Map<String, Object> toParamMap(Customer customer) {
        return new HashMap<>(){
            {
                put("customerId", customer.getCustomerId().toString().getBytes());
                put("customerType", customer.getCustomerType().toString());
                put("name", customer.getName());
                put("createdAt", customer.getCreatedAt());
                put("modifiedAt", customer.getModifiedAt());
            }
        };
    }

    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var customerType = CustomerType.valueOf(resultSet.getString("customer_type").toUpperCase());
        var name = resultSet.getString("name");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var modifiedAt = resultSet.getTimestamp("modified_at") == null ? null : resultSet.getTimestamp("modified_at").toLocalDateTime();

        return new Customer(customerId, customerType, name, createdAt, modifiedAt);
    };

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
