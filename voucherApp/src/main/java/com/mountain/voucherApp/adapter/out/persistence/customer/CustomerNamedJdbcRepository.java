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
import static com.mountain.voucherApp.shared.constants.Message.*;
import static com.mountain.voucherApp.shared.constants.Query.*;
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
            put(CUSTOMER_ID_CAMEL, customerDto.getCustomerId().toString().getBytes(StandardCharsets.UTF_8));
            put(VOUCHER_ID_CAMEL, customerDto.getVoucherId() != null ? customerDto.getVoucherId().toString().getBytes(StandardCharsets.UTF_8) : null);
            put(NAME, customerDto.getCustomerName());
            put(EMAIL, customerDto.getEmail());
            put(CREATED_AT_CAMEL, Timestamp.valueOf(customerDto.getCreatedAt()));
            put(LAST_LOGIN_AT_CAMEL, customerDto.getLastLoginAt() != null ? Timestamp.valueOf(customerDto.getLastLoginAt()) : null);
        }};
        return paramMap;
    }

    @Override
    public CustomerDto insert(CustomerDto customerDto) {
        Map paramMap = toParamMap(customerDto);
        int executeUpdate = jdbcTemplate.update(
                INSERT_CUSTOMER,
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
                UPDATE_CUSTOMER,
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
            put(CUSTOMER_ID_CAMEL, customerId.toString().getBytes(StandardCharsets.UTF_8));
            put(VOUCHER_ID_CAMEL, voucherId != null ? voucherId.toString().getBytes(StandardCharsets.UTF_8) : null);

        }};
        int executeUpdate = jdbcTemplate.update(
                UPDATE_VOUCHER_ID,
                paramMap
        );
        if (executeUpdate != EXECUTE_SUCCESS) {
            throw new RuntimeException(NOT_UPDATED);
        }
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(
                COUNT_CUSTOMER,
                Collections.emptyMap(),
                Integer.class
        );
    }

    @Override
    public List<CustomerDto> findAll() {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMER, customerRowMapper)
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
                                    SELECT_CUSTOMER_BY_ID,
                                    Collections.singletonMap(CUSTOMER_ID_CAMEL, customerId.toString().getBytes()),
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
            put(VOUCHER_ID_CAMEL, voucherId != null ? voucherId.toString().getBytes(StandardCharsets.UTF_8) : null);
        }};
        return jdbcTemplate.query(
                SELECT_CUSTOMER_BY_VOUCHER_ID,
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
                    SELECT_CUSTOMER_BY_NAME,
                    Collections.singletonMap(NAME, name),
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
                    SELECT_CUSTOMER_BY_EMAIL,
                    Collections.singletonMap(EMAIL, email),
                    customerRowMapper
            )));
        } catch (EmptyResultDataAccessException e) {
            log.error(EMPTY_RESULT_ERROR, e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_CUSTOMER, Collections.emptyMap());
    }

    @Override
    public List<CustomerDto> findByVoucherIdNotNull() {
        return jdbcTemplate.query(SELECT_ALL_VOUCHER_ID_NOT_NULL, customerRowMapper)
                .stream()
                .map(customerEntity -> customerMapper.mapToDomainEntity(customerEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void removeByCustomerId(UUID customerId) {
        jdbcTemplate.update(DELETE_BY_CUSTOMER_ID, Collections.singletonMap(CUSTOMER_ID_CAMEL,
                customerId.toString().getBytes(StandardCharsets.UTF_8)));
    }

    private static RowMapper<CustomerEntity> customerRowMapper = new RowMapper<CustomerEntity>() {
        @Override
        public CustomerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            String customerName = rs.getString(NAME);
            byte[] voucherId = rs.getBytes(VOUCHER_ID);
            String email = rs.getString(EMAIL);
            byte[] customerId = rs.getBytes(CUSTOMER_ID);
            LocalDateTime lastLoginAt = rs.getTimestamp(LAST_LOGIN_AT) != null ?
                    rs.getTimestamp(LAST_LOGIN_AT).toLocalDateTime() : null;
            LocalDateTime createdAt = rs.getTimestamp(CREATED_AT).toLocalDateTime();
            UUID customerUUID = toUUID(customerId);
            UUID voucherUUID = voucherId != null ? toUUID(voucherId) : null;
            lastLoginAt = (lastLoginAt != null) ? lastLoginAt : null;
            return new CustomerEntity(customerUUID, voucherUUID, customerName, email, lastLoginAt, createdAt);
        }
    };

}
