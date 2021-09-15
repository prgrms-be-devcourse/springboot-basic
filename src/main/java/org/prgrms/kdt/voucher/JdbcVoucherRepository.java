package org.prgrms.kdt.voucher;

import org.prgrms.kdt.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("local")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> rowMapper = new RowMapper<>() {
        @Override
        public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException {
            var voucherId = byteToUUID(rs.getBytes("voucher_id"));
            var voucherType = rs.getString("voucher_type");
            var discount = rs.getLong("discount");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);
            return createVoucher(voucherId, VoucherType.toVoucherType(voucherType), discount, createdAt);
        }
    };

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static UUID byteToUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        var customerId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return customerId;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(?)", rowMapper, voucherId.toString()));
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("id에 해당하는 바우처가 없습니다");
            return Optional.empty();
        }
    }

    private Voucher createVoucher(UUID voucherId, VoucherType voucherType, long discount, LocalDateTime createdAt) {
        Voucher voucher = null;
        switch (voucherType) {
            case FIXED:
                voucher = new FixedAmountVoucher(voucherId, discount, createdAt);
                break;
            case PERCENT:
                voucher = new PercentDiscountVoucher(voucherId, discount, createdAt);
                break;
        }
        return voucher;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var insert = jdbcTemplate.update("INSERT INTO vouchers (voucher_id, voucher_type, discount, created_at) VALUES (UUID_TO_BIN(?), ?,?,?)",
                voucher.getId().toString().getBytes(StandardCharsets.UTF_8),
                voucher.getType().toString(),
                voucher.getDiscount(),
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return voucher;
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM vouchers", Integer.class);
    }

    @Override
    public List<Voucher> getListByType(VoucherType voucherType) {
        if (voucherType == VoucherType.ALL) return jdbcTemplate.query("SELECT * FROM vouchers", rowMapper);
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE voucher_type = ?", rowMapper, voucherType.toString());
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers");
    }

}
