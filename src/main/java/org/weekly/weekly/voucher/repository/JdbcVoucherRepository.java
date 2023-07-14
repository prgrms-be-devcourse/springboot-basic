package org.weekly.weekly.voucher.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.weekly.weekly.util.ExceptionMsg;
import org.weekly.weekly.voucher.domain.DiscountType;
import org.weekly.weekly.voucher.domain.Voucher;
import org.weekly.weekly.voucher.exception.VoucherException;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("!dev")
@Repository
public class JdbcVoucherRepository implements VoucherRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapToVoucher(rs), uuidToBytes(voucherId)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM vouchers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapToVoucher(rs));
    }

    @Override
    public List<Voucher> findByDiscountType(DiscountType discountType) {
        String sql = "SELECT * FROM vouchers WHERE discount = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapToVoucher(rs), discountType.name());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO vouchers(voucher_id, amount, discount, registration_date, expiration_date) VALUES (UUID_TO_BIN(?), ?, ?, ?, ?)";
        int update = 0;
        try {
            update = jdbcTemplate.update(sql,
                    uuidToBytes(voucher.getVoucherId()),
                    voucher.getAmount(),
                    voucher.getDiscountType().name(),
                    Timestamp.valueOf(voucher.getRegistrationDate().atStartOfDay()),
                    Timestamp.valueOf(voucher.getExpirationDate().atStartOfDay()));
        } catch(DataAccessException dataAccessException) {
            throw new VoucherException(ExceptionMsg.SQL_INSERT_ERROR);
        }

        if (update != 1) {
            throw new VoucherException(ExceptionMsg.SQL_ERROR);
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        String sql = "UPDATE vouchers SET amount = ?, discount = ?, expiration_date = ? WHERE voucher_id = UUID_TO_BIN(?)";

        int update = jdbcTemplate.update(sql,
                voucher.getAmount(),
                voucher.getDiscountType().name(),
                Timestamp.valueOf(voucher.getExpirationDate().atStartOfDay()),
                uuidToBytes(voucher.getVoucherId()));
        if (update != 1) {
            throw new VoucherException(ExceptionMsg.SQL_ERROR);
        }
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
        jdbcTemplate.update(sql, uuidToBytes(voucherId));
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM vouchers";
        jdbcTemplate.update(sql);
    }

    public static UUID toUUID(byte[] bytes) {
        var buffer = ByteBuffer.wrap(bytes);
        return new UUID(buffer.getLong(), buffer.getLong());
    }
    
    private byte[] uuidToBytes(UUID voucherId) {
        return voucherId.toString().getBytes();
    }

    private Voucher mapToVoucher(ResultSet resultSet) throws SQLException {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long amount = resultSet.getLong("amount");
        DiscountType discountType = DiscountType.getDiscountTypeByName(resultSet.getString("discount"));
        LocalDate registrationDate = resultSet.getTimestamp("registration_date") == null ? null : resultSet.getTimestamp("registration_date").toLocalDateTime().toLocalDate();
        LocalDate expirationDate = resultSet.getTimestamp("expiration_date") == null ? null : resultSet.getTimestamp("expiration_date").toLocalDateTime().toLocalDate();

        return new Voucher(voucherId,amount, registrationDate, expirationDate, discountType.getNewInstance());
    }

    

}
