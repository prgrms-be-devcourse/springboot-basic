package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.global.utils.TimeUtil;
import com.programmers.vouchermanagement.global.utils.UuidUtil;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherPolicy;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotUpdatedException;
import com.programmers.vouchermanagement.voucher.mapper.VoucherPolicyMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String CREATE = "INSERT INTO voucher(voucher_id, discount, voucher_type, created_at) VALUES(UUID_TO_BIN(?), (?), (?), (?))";
    private static final String READ_ALL = "SELECT * FROM voucher";
    private static final String READ_ONCE = "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String READ_CONDITION = "SELECT * FROM voucher WHERE created_at >= (?) AND voucher_type = (?)";
    private static final String UPDATE = "UPDATE voucher SET discount = ?, voucher_type = ? WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE_ALL = "DELETE FROM voucher";
    private static final String DELETE_ONCE = "DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(?)";

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, index) -> {

        UUID voucherId = UuidUtil.bytesToUUID(resultSet.getBytes("voucher_id"));
        Long discount = resultSet.getLong("discount");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        VoucherPolicy voucherPolicy = VoucherPolicyMapper.toEntity(discount, voucherType);
        LocalDateTime createdAt = TimeUtil.timestampToLocalDateTime(resultSet.getTimestamp("created_at"));

        return new Voucher(voucherId, voucherType, voucherPolicy, createdAt);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {
        jdbcTemplate.update(CREATE,
                voucher.getVoucherId().toString(),
                voucher.getVoucherPolicy().getDiscount(),
                voucher.getVoucherType().toString(),
                voucher.getCreatedAt());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(READ_ALL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        try {
            return Optional.of(jdbcTemplate.queryForObject(READ_ONCE, voucherRowMapper, voucherId.toString()));

        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Voucher voucher) {

        int isUpdated = jdbcTemplate.update(UPDATE,
                voucher.getVoucherPolicy().getDiscount(),
                voucher.getVoucherType().toString(),
                voucher.getVoucherId().toString());

        if (isUpdated != 1) {
            throw new VoucherNotUpdatedException();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(DELETE_ONCE, voucherId.toString());
    }

    @Override
    public List<Voucher> findAllByCreatedAtAndVoucherType(LocalDateTime createdAt, VoucherType voucherType) {
        return jdbcTemplate.query(READ_CONDITION, voucherRowMapper, createdAt, voucherType);
    }
}
