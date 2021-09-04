package com.prgrms.devkdtorder.customervoucher.repository;

import com.prgrms.devkdtorder.customervoucher.domain.CustomerVoucher;
import com.prgrms.devkdtorder.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class JdbcCustomerVoucherRepository implements CustomerVoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcCustomerVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<CustomerVoucher> customerVoucherRowMapper = (resultSet, i) -> {
        var customerVoucherId = Utils.toUUID(resultSet.getBytes("customer_voucher_id"));
        var customerId = Utils.toUUID(resultSet.getBytes("customer_id"));
        var voucherId = Utils.toUUID(resultSet.getBytes("voucher_id"));
        var used = resultSet.getBoolean("used");
        var usedAt = resultSet.getTimestamp("used_at") != null ?
                resultSet.getTimestamp("used_at").toLocalDateTime() : null;
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);
        var expiredAt = resultSet.getTimestamp("expired_at") != null ?
                resultSet.getTimestamp("expired_at").toLocalDateTime() : null;
        return CustomerVoucher.builder()
                .customerVoucherId(customerVoucherId)
                .customerId(customerId)
                .voucherId(voucherId)
                .used(used)
                .usedAt(usedAt)
                .createdAt(createdAt)
                .expiredAt(expiredAt)
                .build();
    };


    private Map<String, Object> toParamMap(CustomerVoucher customerVoucher) {
        return new HashMap<>() {{
            put("customerVoucherId", customerVoucher.getCustomerId().toString().getBytes());
            put("customerId", customerVoucher.getCustomerId().toString().getBytes());
            put("voucherId", customerVoucher.getCustomerId().toString().getBytes());
            put("used", customerVoucher.isUsed());
            put("usedAt", customerVoucher.getUsedAt() != null ? Timestamp.valueOf(customerVoucher.getUsedAt()) : null);
            put("expiredAt", customerVoucher.getExpiredAt() != null ? Timestamp.valueOf(customerVoucher.getExpiredAt()) : null);
            put("createdAt", Timestamp.valueOf(customerVoucher.getCreatedAt()));
        }};
    }

    @Override
    public CustomerVoucher insert(CustomerVoucher customerVoucher) {
        String sql = "INSERT INTO custome_voucher(customer_voucher_id, customer_id, voucher_id, created_at, expired_at)" +
                " VALUES (UNHEX(REPLACE(:customerVoucherId, '-', '')), UNHEX(REPLACE(:customerId, '-', '')), UNHEX(REPLACE(:voucherId, '-', '')), :createdAt, :expiredAt)";
        int insert = jdbcTemplate.update(sql, toParamMap(customerVoucher));
        if (insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return customerVoucher;
    }


    @Override
    public CustomerVoucher update(CustomerVoucher customerVoucher) {
        String sql = "UPDATE custome_voucher" +
                " SET used = :used, used_at = :usedAt, expired_at = :expiredAt" +
                " WHERE customer_voucher_id = UNHEX(REPLACE(:customerVoucherId, '-', ''))";
        int update = jdbcTemplate.update(sql, toParamMap(customerVoucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return customerVoucher;
    }

    @Override
    public List<CustomerVoucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer_voucher", customerVoucherRowMapper);
    }

    @Override
    public Optional<CustomerVoucher> findById(UUID customerVoucherId) {
        try {
            String sql = "SELECT * FROM customer_voucher WHERE customer_voucher_id = UNHEX(REPLACE(:customerVoucherId, '-', ''))";
            CustomerVoucher customerVoucher = jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("customerVoucherId", customerVoucherId.toString().getBytes()),
                    customerVoucherRowMapper);
            return Optional.ofNullable(customerVoucher);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }

    }

    @Override
    public List<CustomerVoucher> findByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM customer_voucher WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))";
        return jdbcTemplate.query(sql,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                customerVoucherRowMapper);
    }

    @Override
    public List<CustomerVoucher> findByVoucherId(UUID voucherId) {
        String sql = "SELECT * FROM customer_voucher WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
        return jdbcTemplate.query(sql,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                customerVoucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM customer_voucher");
    }

    @Override
    public void deleteById(UUID customerVoucherId) {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM customer_voucher WHERE customer_voucher_id = UNHEX(REPLACE(:customerVoucherId, '-', ''))");
    }
}
