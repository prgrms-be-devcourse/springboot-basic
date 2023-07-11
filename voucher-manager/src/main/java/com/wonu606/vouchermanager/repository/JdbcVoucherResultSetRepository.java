package com.wonu606.vouchermanager.repository;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherResultSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JdbcVoucherResultSetRepository implements VoucherResultSetRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherResultSetRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (isVoucherIdPresent(voucher)) {
            updateVoucher(voucher);
        }
        insertVoucher(voucher);
        return voucher;
    }

    @Override
    public Optional<VoucherResultSet> findById(UUID id) {
        String selectSql = "SELECT * FROM voucher WHERE voucher_id = ?";
        try {
            VoucherResultSet voucherResultSet = jdbcTemplate.queryForObject(selectSql,
                    voucherResultSetRowMapper(), id);
            return Optional.ofNullable(voucherResultSet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherResultSet> findAll() {
        String selectSql = "SELECT * FROM voucher";
        return jdbcTemplate.query(selectSql, voucherResultSetRowMapper());
    }

    @Override
    public void deleteById(UUID id) {
        String deleteSql = "DELETE FROM voucher WHERE voucher_id = ?";
        jdbcTemplate.update(deleteSql, id.toString());
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM voucher";
        jdbcTemplate.update(deleteSql);
    }

    private RowMapper<VoucherResultSet> voucherResultSetRowMapper() {
        return (resultSet, rowNum) -> {
            String voucherSimpleName = resultSet.getString("voucher_type");
            UUID uuid = UUID.fromString(resultSet.getString("voucher_id"));
            double discountValue = resultSet.getDouble("discount_value");

            return new VoucherResultSet(uuid, voucherSimpleName, discountValue);
        };
    }

    private boolean isVoucherIdPresent(Voucher voucher) {
        String selectSql = "SELECT count(*) FROM voucher WHERE voucher_id = ?";
        Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class,
                voucher.getUuid().toString());
        return count != null && count >= 0;
    }

    private void updateVoucher(Voucher voucher) {
        String updateSql = "UPDATE voucher SET voucher_type = ?, discount_value = ? WHERE voucher_id = ?";
        jdbcTemplate.update(updateSql, voucher.getClass().getSimpleName(),
                voucher.getDiscountValue(), voucher.getUuid().toString());
    }

    private void insertVoucher(Voucher voucher) {
        String insertSql = "INSERT INTO voucher (voucher_id, voucher_type, discount_value) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertSql, voucher.getUuid().toString(),
                voucher.getClass().getSimpleName(), voucher.getDiscountValue());
    }
}
