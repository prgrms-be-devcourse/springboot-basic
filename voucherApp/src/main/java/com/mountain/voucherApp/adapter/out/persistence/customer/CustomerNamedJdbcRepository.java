package com.mountain.voucherApp.adapter.out.persistence.customer;

import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.application.port.out.CustomerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.mountain.voucherApp.shared.constants.Field.*;
import static com.mountain.voucherApp.shared.constants.ErrorMessage.*;
import static com.mountain.voucherApp.shared.constants.Number.EXECUTE_SUCCESS;
import static com.mountain.voucherApp.shared.utils.CommonUtil.toUUID;

@Repository
public class CustomerNamedJdbcRepository implements CustomerPort {

    private static final Logger log = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate,
                                       CustomerMapper customerMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerMapper = customerMapper;
    }

    private Map<String, Object> toParamMap(CustomerDto customerDto) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put(CUSTOMER_ID_CAMEL.getValue(), customerDto.getCustomerId().toString().getBytes(StandardCharsets.UTF_8));
            put(VOUCHER_ID_CAMEL.getValue(), customerDto.getVoucherId() != null ? customerDto.getVoucherId().toString().getBytes(StandardCharsets.UTF_8) : null);
            put(NAME.getValue(), customerDto.getCustomerName());
            put(EMAIL.getValue(), customerDto.getEmail());
            put(CREATED_AT_CAMEL.getValue(), Timestamp.valueOf(customerDto.getCreatedAt()));
            put(LAST_LOGIN_AT_CAMEL.getValue(), customerDto.getLastLoginAt() != null ? Timestamp.valueOf(customerDto.getLastLoginAt()) : null);
        }};
        return paramMap;
    }

    @Override
    public CustomerDto insert(CustomerDto customerDto) {
        Map paramMap = toParamMap(customerDto);
        int executeUpdate = jdbcTemplate.update(
                "INSERT INTO customers (customer_id, voucher_id, name, email, created_at) VALUES (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId), :name, :email, :createdAt)",
                paramMap
        );
        if (executeUpdate != EXECUTE_SUCCESS) {
            throw new RuntimeException(NOT_INSERTED);
        }
        return customerDto;
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Map paramMap = toParamMap(customerDto);
        int executeUpdate = jdbcTemplate.update(
                "UPDATE customers SET voucher_id = UUID_TO_BIN(:voucherId), name = :name, email = :email, last_login_at = :lastLoginAt where customer_id = UUID_TO_BIN(:customerId)",
                paramMap
        );
        if (executeUpdate != EXECUTE_SUCCESS) {
            throw new RuntimeException(NOT_UPDATED);
        }
        return customerDto;
    }

    @Override
    public void updateVoucherId(UUID customerId, UUID voucherId) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put(CUSTOMER_ID_CAMEL.getValue(), customerId.toString().getBytes(StandardCharsets.UTF_8));
            put(VOUCHER_ID_CAMEL.getValue(), voucherId != null ? voucherId.toString().getBytes(StandardCharsets.UTF_8) : null);

        }};
        int executeUpdate = jdbcTemplate.update(
                "UPDATE customers SET voucher_id = UUID_TO_BIN(:voucherId) where customer_id = UUID_TO_BIN(:customerId)",
                paramMap
        );
        if (executeUpdate != EXECUTE_SUCCESS) {
            throw new RuntimeException(NOT_UPDATED);
        }
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(
                "select count(*) from customers",
                Collections.emptyMap(),
                Integer.class
        );
    }

    @Override
    public List<CustomerDto> findAll() {
        return jdbcTemplate.query("select * from customers", customerRowMapper)
                .stream()
                .map((customerEntity) -> customerMapper.mapToDomainEntity(customerEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> findById(UUID customerId) {
        try {
            return Optional.ofNullable(
                    customerMapper.mapToDomainEntity(
                            jdbcTemplate.queryForObject(
                                    "select * from customers WHERE customer_id = UUID_TO_BIN(:customerId)",
                                    Collections.singletonMap(CUSTOMER_ID_CAMEL.getValue(), customerId.toString().getBytes()),
                                    customerRowMapper
                            )
                    ));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT_ERROR, e);
            return Optional.empty();
        }
    }

    @Override
    public List<CustomerDto> findByVoucherId(UUID voucherId) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put(VOUCHER_ID_CAMEL.getValue(), voucherId != null ? voucherId.toString().getBytes(StandardCharsets.UTF_8) : null);
        }};
        return jdbcTemplate.query(
                "select * from customers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                paramMap,
                customerRowMapper
        ).stream()
                .map((customerEntity) -> customerMapper.mapToDomainEntity(customerEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> findByName(String name) {
        try {
            return Optional.ofNullable(customerMapper.mapToDomainEntity(jdbcTemplate.queryForObject(
                    "select * from customers WHERE name = :name",
                    Collections.singletonMap(NAME.getValue(), name),
                    customerRowMapper
            )));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT_ERROR, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CustomerDto> findByEmail(String email) {
        try {
            return Optional.ofNullable(customerMapper.mapToDomainEntity(jdbcTemplate.queryForObject(
                    "select * from customers WHERE email = :email",
                    Collections.singletonMap(EMAIL.getValue(), email),
                    customerRowMapper
            )));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT_ERROR, e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM customers", Collections.emptyMap());
    }

    @Override
    public List<CustomerDto> findByVoucherIdNotNull() {
        return jdbcTemplate.query("select * from customers where voucher_id is not null", customerRowMapper)
                .stream()
                .map(customerEntity -> customerMapper.mapToDomainEntity(customerEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void removeByCustomerId(UUID customerId) {
        jdbcTemplate.update("DELETE FROM customers where customer_id = UUID_TO_BIN(:customerId)", Collections.singletonMap(CUSTOMER_ID_CAMEL.getValue(),
                customerId.toString().getBytes(StandardCharsets.UTF_8)));
    }

    private static RowMapper<CustomerEntity> customerRowMapper = new RowMapper<CustomerEntity>() {
        @Override
        public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            String customerName = rs.getString(NAME.getValue());
            byte[] voucherId = rs.getBytes(VOUCHER_ID.getValue());
            String email = rs.getString(EMAIL.getValue());
            byte[] customerId = rs.getBytes(CUSTOMER_ID.getValue());
            LocalDateTime lastLoginAt = rs.getTimestamp(LAST_LOGIN_AT.getValue()) != null ?
                    rs.getTimestamp(LAST_LOGIN_AT.getValue()).toLocalDateTime() : null;
            LocalDateTime createdAt = rs.getTimestamp(CREATED_AT.getValue()).toLocalDateTime();
            UUID customerUUID = toUUID(customerId);
            UUID voucherUUID = voucherId != null ? toUUID(voucherId) : null;
            lastLoginAt = (lastLoginAt != null) ? lastLoginAt : null;
            return new CustomerEntity(customerUUID, voucherUUID, customerName, email, lastLoginAt, createdAt);
        }
    };

}
