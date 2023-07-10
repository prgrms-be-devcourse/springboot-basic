package com.prgms.VoucherApp.domain.voucher.model;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class VoucherJdbcDao implements VoucherDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VoucherJdbcDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "INSERT INTO voucher VALUES (:id, :amount, :type)";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", voucher.getVoucherId().toString())
            .addValue("amount", voucher.getAmount())
            .addValue("type", voucher.getVoucherType().getVoucherTypeName());

        namedParameterJdbcTemplate.update(sql, paramMap);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM voucher";
        List<Voucher> vouchers = namedParameterJdbcTemplate.query(sql, voucherRowMapper());
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "SELECT * FROM voucher where id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", voucherId);
        try {
            Voucher voucher = namedParameterJdbcTemplate.queryForObject(sql, paramMap, voucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType type) {
        String sql = "SELECT * FROM voucher where type = :type";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("type", type.getVoucherTypeName());

        List<Voucher> vouchers = namedParameterJdbcTemplate.query(sql, paramMap, voucherRowMapper());
        return vouchers;
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "UPDATE voucher SET amount = :amount, type = :type WHERE id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("amount", voucher.getAmount())
            .addValue("type", voucher.getVoucherType().getVoucherTypeName())
            .addValue("id", voucher.getVoucherId().toString());

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "DELETE FROM voucher WHERE id = :id";
        MapSqlParameterSource paramMap = new MapSqlParameterSource()
            .addValue("id", voucherId.toString());

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            BigDecimal amount = resultSet.getBigDecimal("amount");
            String type = resultSet.getString("type");
            VoucherType voucherType = VoucherType.findByVoucherTypeName(type);
            if (voucherType.isFixedVoucher()) {
                return new FixedAmountVoucher(UUID.fromString(id), amount);
            }

            return new PercentDiscountVoucher(UUID.fromString(id), amount);
        };
    }

}
