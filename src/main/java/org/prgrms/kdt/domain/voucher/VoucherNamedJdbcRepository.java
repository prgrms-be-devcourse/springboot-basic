package org.prgrms.kdt.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.customer.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Repository
@Primary
public class VoucherNamedJdbcRepository implements VoucherRepository {

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = :voucherId";
    private static final String SELECT_BY_TYPE_SQL = "SELECT * FROM vouchers WHERE type = :type";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM vouchers";
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, type, amount) VALUES (:voucherId, :type, :amount)";
    private static final String UPDATE_CUSTOMER_ID_SQL = "UPDATE vouchers SET customer_id = :customerId WHERE voucher_id = :voucherId";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var type = resultSet.getString("type");
        if (VoucherType.valueOf(type).equals(VoucherType.FIXED_AMOUNT)) {
            return FixedAmountVoucher.builder()
                    .voucherId(resultSet.getLong("voucher_id"))
                    .type(VoucherType.valueOf(resultSet.getString("type")))
                    .amount(resultSet.getLong("amount"))
                    .customerId(resultSet.getLong("customer_id"))
                    .build();
        } else {
            return PercentDiscountVoucher.builder()
                    .voucherId(resultSet.getLong("voucher_id"))
                    .type(VoucherType.valueOf(resultSet.getString("type")))
                    .amount(resultSet.getLong("amount"))
                    .customerId(resultSet.getLong("customer_id"))
                    .build();
        }
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        log.info("[*] now: {}", voucher.getType().toString());
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId());
            put("type", voucher.getType().toString());
            put("amount", voucher.getAmount());
        }};
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        log.info("[*] voucherId: {}", voucherId);
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                            Collections.singletonMap("voucherId", voucherId),
                            voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        List<Voucher> vouchers = jdbcTemplate.query(SELECT_BY_TYPE_SQL, voucherRowMapper);
        for (Voucher voucher : vouchers) {
            log.info("[*] voucher: {}", voucher);
        }
        return jdbcTemplate.query(SELECT_BY_TYPE_SQL, voucherRowMapper);
    }


    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update(INSERT_SQL,
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Transactional
    public Voucher update(Voucher voucher, Customer customer) {
        var update = jdbcTemplate.update(
                UPDATE_CUSTOMER_ID_SQL, new HashMap<>() {{
                    put("voucherId", voucher.getVoucherId());
                    put("customerId", customer.getCustomerId());
                }});
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Collections.emptyMap(), Integer.class);
    }
}
