package org.prgrms.kdt.storage;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("mysql")
public class JdbcVoucherStorage implements VoucherStorage {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherStorage(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void saveVoucher(Voucher newVoucher) {
        String saveVoucherSql = "insert vouchers(voucher_id,voucher_name,discount_value) values(UUID_TO_BIN(:voucherId),:voucherName,:discountValue)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("voucherId", newVoucher.getVoucherId().toString().getBytes())
            .addValue("voucherName", newVoucher.getVoucherName())
            .addValue("discountValue", String.valueOf(newVoucher.getVoucherDiscountValue()));
        jdbcTemplate.update(saveVoucherSql, parameterSource);
    }

    @Override
    public List<Voucher> findAllVoucher() {
        String findAllVoucherSql = "select voucher_id,voucher_name,discount_value from vouchers";
        return jdbcTemplate.query(findAllVoucherSql, voucherRowMapper());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return ((rs, rowNum) -> {
            UUID voucherId = toUUID(rs.getBytes("voucher_id"));
            String voucherName = rs.getString("voucher_name");
            long discountValue = Long.parseLong(rs.getString("discount_value"));
            return voucherName.equals(FixedAmountVoucher.class.getSimpleName())
                ? new FixedAmountVoucher(voucherId, discountValue) : new PercentDiscountVoucher(voucherId, discountValue);
        });
    }

    private UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public void clearStorage() {
        String clearStorageSql = "delete from vouchers";
        Map<String, Object> param = Map.of();
        jdbcTemplate.update(clearStorageSql, param);
    }

    public Voucher update(Voucher voucher) {
        String updateSql = "update vouchers set discount_value=:discountValue where voucher_id=UUID_TO_BIN(:voucherId)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("discountValue", String.valueOf(voucher.getVoucherDiscountValue()))
            .addValue("voucherId", voucher.getVoucherId().toString().getBytes());
        jdbcTemplate.update(updateSql, parameterSource);
        return voucher;
    }

    public Optional<Voucher> findById(UUID voucherId) {
        String findByIdSql = "select voucher_id,voucher_name,discount_value from vouchers where voucher_id=UUID_TO_BIN(:voucherId)";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
            .addValue("voucherId", voucherId.toString().getBytes());
        try {
            Voucher voucher = jdbcTemplate.queryForObject(findByIdSql, parameterSource, voucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
