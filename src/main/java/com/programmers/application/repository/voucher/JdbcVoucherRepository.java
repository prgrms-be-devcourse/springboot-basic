package com.programmers.application.repository.voucher;

import com.programmers.application.domain.voucher.FixedAmountVoucher;
import com.programmers.application.domain.voucher.PercentDiscountVoucher;
import com.programmers.application.domain.voucher.Voucher;
import com.programmers.application.domain.voucher.VoucherType;
import com.programmers.application.repository.sql.builder.InsertSqlBuilder;
import com.programmers.application.repository.sql.builder.SelectSqlBuilder;
import com.programmers.application.util.UUIDMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = new InsertSqlBuilder()
                .insertInto("voucher")
                .columns("voucher_id, discount_amount, type")
                .values(":voucherId, :discountAmount, :type")
                .build();
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", UUIDMapper.toBytes(voucher.getVoucherId()))
                .addValue("discountAmount", voucher.getDiscountAmount())
                .addValue("type", voucher.getVoucherType().name());
        namedParameterJdbcTemplate.update(sql, paramMap);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = new SelectSqlBuilder()
                .select("*")
                .from("voucher")
                .build();
        return namedParameterJdbcTemplate.query(sql, getVoucherRowMapper());
    }

    @Override
    public Optional<Voucher> findByVoucherId(UUID voucherId) {
        String sql = new SelectSqlBuilder()
                .select("*")
                .from("voucher")
                .where("voucher_id = :voucherId")
                .build();
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", UUIDMapper.toBytes(voucherId));
        try {
            Voucher voucher = namedParameterJdbcTemplate.queryForObject(sql, paramMap, getVoucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Voucher> getVoucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUIDMapper.toUUID(rs.getBytes("voucher_id"));
            long discountAmount = rs.getInt("discount_amount");
            String type = rs.getString("type");
            VoucherType voucherType = VoucherType.valueOf(type.toUpperCase());
            return switch (voucherType) {
                case FIXED -> FixedAmountVoucher.of(voucherId, discountAmount);
                case PERCENT -> PercentDiscountVoucher.of(voucherId, discountAmount);
            };
        };
    }
}
