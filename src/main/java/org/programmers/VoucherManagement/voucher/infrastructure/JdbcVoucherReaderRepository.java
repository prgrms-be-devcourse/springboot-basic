package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.domain.VoucherFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherReaderRepository implements VoucherReaderRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherReaderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT voucher_id, voucher_value, voucher_type FROM voucher_table";
        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "SELECT voucher_id, voucher_value, voucher_type FROM voucher_table WHERE voucher_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    voucherRowMapper(),
                    voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public static RowMapper<Voucher> voucherRowMapper() {
        return (result, rowNum) -> VoucherFactory.mapVoucher(
                UUID.fromString(result.getString("voucher_id")),
                result.getInt("voucher_value"),
                DiscountType.from(result.getString("voucher_type"))
        );
    }
}
