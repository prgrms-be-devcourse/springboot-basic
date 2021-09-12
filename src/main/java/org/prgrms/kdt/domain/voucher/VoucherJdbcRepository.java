package org.prgrms.kdt.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.kdt.domain.customer.Customer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Profile({"local", "test"})
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class VoucherJdbcRepository implements VoucherRepository {

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = ?";
    private static final String SELECT_BY_TYPE_SQL = "SELECT * FROM vouchers WHERE type = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM vouchers";
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, type, amount) VALUES (?, ?, ?)";
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

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                            voucherRowMapper,
                            voucherId));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        return jdbcTemplate.query(SELECT_BY_TYPE_SQL, voucherRowMapper);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update(INSERT_SQL,
                voucher.getVoucherId(),
                voucher.getType(),
                voucher.getAmount()
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher, Customer customer) {
        return null;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
    }
}
