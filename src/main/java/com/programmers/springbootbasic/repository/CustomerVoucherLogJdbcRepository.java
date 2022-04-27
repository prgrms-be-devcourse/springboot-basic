package com.programmers.springbootbasic.repository;

import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.CustomerVoucherLog;
import com.programmers.springbootbasic.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerVoucherLogJdbcRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerVoucherLogJdbcRepository.class);

    private static final String INSERT =
            "INSERT INTO customer_voucher_log(customer_id, voucher_id) VALUES(?, ?)";
    private static final String SELECT_BY_CUSTOMER_ID =
            "SELECT * FROM customer_voucher_log WHERE customer_id = ?";
    private static final String SELECT_BY_VOUCHER_ID =
            "SELECT * FROM customer_voucher_log WHERE voucher_id = ?";
    private static final String SELECT_ALL =
            "SELECT * FROM customer_voucher_log";

    private static final String SELECT_VOUCHERS_BY_CUSTOMER_ID =
            "SELECT v.voucher_id, v.created_at, v.fixed_amount, v.discount_percent, v.type " +
            "From customers c, customer_voucher_log s, vouchers v " +
            "WHERE s.voucher_id = v.voucher_id AND s.customer_id = c.customer_id AND s.customer_id = ?";
    private static final String SELECT_CUSTOMER_BY_VOUCHER_ID =
            "SELECT c.customer_id, c.name, s.registration_date " +
                    "FROM customers c, customer_voucher_log s, vouchers v " +
                    "WHERE s.voucher_id = v.voucher_id AND s.customer_id = c.customer_id AND s.voucher_id = ?";

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<CustomerVoucherLog> customerVoucherLogRowMapper = (rs, rowNum) -> {
        String customerId = rs.getString("customer_id");
        UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
        LocalDateTime registrationDate = rs.getTimestamp("registration_date").toLocalDateTime();

        return new CustomerVoucherLog(customerId, voucherId, registrationDate);
    };

    private static final RowMapper<Voucher> vouchersRowMapper = (rs, rowNum) -> {
        String voucherId = rs.getString("voucher_id");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        Long fixed_amount = rs.getLong("fixed_amount");
        fixed_amount = fixed_amount == 0 ? null : fixed_amount;

        Integer discount_percent = rs.getInt("discount_percent");
        discount_percent = discount_percent == 0 ? null : discount_percent;

        Integer type = rs.getInt("type");

        return new Voucher(UUID.fromString(voucherId), createdAt, fixed_amount, discount_percent, type);
    };

    private static final RowMapper<Customer> customersRowMapper = (rs, rowNum) -> {
        String customerId = rs.getString("customer_id");
        String name = rs.getString("name");
        LocalDateTime registrationDate = rs.getTimestamp("registration_date").toLocalDateTime();

        return new Customer(customerId, name, registrationDate);
    };

    @Autowired
    public CustomerVoucherLogJdbcRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CustomerVoucherLog insert(String customerId, UUID voucherId) {
        CustomerVoucherLog customerVoucherLog = new CustomerVoucherLog(customerId, voucherId);
        int insertResult = jdbcTemplate.update(INSERT, customerId, voucherId.toString());

        if (insertResult != 1)
            logger.error("새로운 상태 저장 요청 실패");

        return customerVoucherLog;
    }

    public List<CustomerVoucherLog> findByCustomerId(String customerId) {
        return jdbcTemplate.query(SELECT_BY_CUSTOMER_ID, customerVoucherLogRowMapper, customerId);
    }

    public Optional<CustomerVoucherLog> findByVoucherId(UUID voucherId) {
        try {
            CustomerVoucherLog customerVoucherLog = jdbcTemplate.queryForObject(SELECT_BY_VOUCHER_ID, customerVoucherLogRowMapper, voucherId.toString());
            return Optional.ofNullable(customerVoucherLog);
        } catch (DataAccessException e) {
            logger.info("존재하지 않은 할인권 아이디로 상태 정보 검색 요청");
            return Optional.empty();
        }
    }

    public List<CustomerVoucherLog> findAll() {
        return jdbcTemplate.query(SELECT_ALL, customerVoucherLogRowMapper);
    }

    public List<Voucher> findVouchersByCustomerId(String customerId) {
        return jdbcTemplate.query(SELECT_VOUCHERS_BY_CUSTOMER_ID, vouchersRowMapper, customerId);
    }

    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        try {
            Customer customer = jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_VOUCHER_ID, customersRowMapper, voucherId.toString());
            return Optional.ofNullable(customer);
        } catch (DataAccessException e) {
            logger.info("존재하지 않거나 또는 할당되지 않은 할인권 아이디로 해당 하는 할인권을 소유한 고객 정보 요청");
            return Optional.empty();
        }
    }

}