package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.global.common.JdbcRepositoryManager;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String CREATE = "INSERT INTO voucher(voucher_id, discount, voucher_type) VALUES(UUID_TO_BIN(?), (?), (?))";
    private static final String READ_ALL = "SELECT * FROM voucher";
    private static final String READ_ONCE = "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String UPDATE = "UPDATE voucher SET discount = ?, voucher_type = ? WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE_ALL = "DELETE FROM voucher";
    private static final String DELETE_ONCE = "DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(?)";

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, index) -> {

        UUID voucherId = JdbcRepositoryManager.bytesToUUID(resultSet.getBytes("voucher_id"));
        Long discount = resultSet.getLong("discount");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        VoucherPolicy voucherPolicy = VoucherPolicyMapper.toEntity(discount, voucherType);

        return new Voucher(voucherId, voucherType, voucherPolicy);
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
                voucher.getVoucherType().toString());
    }

    @Override
    public List<Voucher> findAll() {

        List<Voucher> vouchers = jdbcTemplate.query(READ_ALL, voucherRowMapper);

        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        try {
            Optional<Voucher> optionalVoucher = Optional.of(jdbcTemplate.queryForObject(READ_ONCE, voucherRowMapper, voucherId.toString()));
            return optionalVoucher;

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
        jdbcTemplate.update(DELETE_ONCE);
    }
}
