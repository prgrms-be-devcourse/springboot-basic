package com.programmers.springbootbasic.domain.userVoucherWallet.infrastructure;

import com.programmers.springbootbasic.domain.user.domain.entity.User;
import com.programmers.springbootbasic.domain.userVoucherWallet.domain.UserVoucherWalletRepository;
import com.programmers.springbootbasic.domain.userVoucherWallet.domain.entity.UserVoucherWallet;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.infrastructure.dto.UserVoucherWalletWithUser;
import com.programmers.springbootbasic.domain.voucher.infrastructure.dto.UserVoucherWalletWithVoucher;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserVoucherWalletRepository implements UserVoucherWalletRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<UserVoucherWallet> voucherWalletRowMapper =
        (rs, rowNum) -> new UserVoucherWallet(
            rs.getLong("id"),
            rs.getLong("user_id"),
            UUID.fromString(rs.getString("voucher_id")),
            toLocalDateTime(rs.getString("created_at"))
        );

    public JdbcUserVoucherWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserVoucherWallet save(UserVoucherWallet entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                .prepareStatement(
                    "INSERT INTO user_voucher_wallet (user_id, voucher_id, created_at) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setLong(1, entity.getUserId());
            ps.setString(2, entity.getVoucherId().toString());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getCreatedAt()));
            return ps;
        }, keyHolder);

        // key 없으면 npe
        return new UserVoucherWallet(
            Objects.requireNonNull(keyHolder.getKey()).longValue(),
            entity.getUserId(),
            entity.getVoucherId(),
            entity.getCreatedAt()
        );
    }

    @Override
    public Optional<UserVoucherWallet> findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM user_voucher_wallet WHERE id = ?",
            voucherWalletRowMapper, id).stream().findFirst();
    }

    @Override
    public List<UserVoucherWallet> findAll() {
        return jdbcTemplate.query("SELECT * FROM user_voucher_wallet", voucherWalletRowMapper);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM user_voucher_wallet WHERE id = ?", id);
    }

    @Override
    public int update(UserVoucherWallet entity) {
        return jdbcTemplate.update(
            "UPDATE user_voucher_wallet SET user_id = ? ,voucher_id = ? WHERE id = ?",
            entity.getUserId(),
            entity.getVoucherId().toString(),
            entity.getId()
        );
    }

    @Override
    public List<UserVoucherWalletWithVoucher> findVoucherByUserId(Long userId) {
        RowMapper<UserVoucherWalletWithVoucher> voucherWalletJoinRowMapper =
            (rs, rowNum) -> new UserVoucherWalletWithVoucher(
                rs.getLong("id"),
                new Voucher(
                    UUID.fromString(rs.getString("voucher_id")),
                    VoucherTypeEnum.of(rs.getString("voucher_type"))
                        .getVoucherType(rs.getInt("benefit_value")),
                    rs.getInt("benefit_value")
                )
            );

        return jdbcTemplate.query("""
                SELECT *
                FROM user_voucher_wallet uvw
                JOIN vouchers v ON uvw.voucher_id = v.id
                WHERE uvw.user_id = ?
                """,
            voucherWalletJoinRowMapper, userId);
    }

    @Override
    public List<UserVoucherWalletWithUser> findUserByVoucherId(UUID voucherId) {
        RowMapper<UserVoucherWalletWithUser> voucherWalletJoinRowMapper =
            (rs, rowNum) -> new UserVoucherWalletWithUser(
                rs.getLong("id"),
                new User(
                    rs.getLong("user_id"),
                    rs.getString("nickname"),
                    rs.getBoolean("blocked")
                )
            );
        return jdbcTemplate.query("""
                SELECT *
                FROM user_voucher_wallet uvw
                JOIN users u ON uvw.user_id = u.id
                WHERE uvw.voucher_id = ?
                """,
            voucherWalletJoinRowMapper, voucherId.toString());
    }

    private LocalDateTime toLocalDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
