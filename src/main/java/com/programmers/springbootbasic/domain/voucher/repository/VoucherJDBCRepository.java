package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.common.utils.UUIDConverter;
import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.service.VoucherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Profile("default")
@Repository
@RequiredArgsConstructor
public class VoucherJDBCRepository implements VoucherRepository {
    private static final String INSERT_QUERY = "INSERT INTO vouchers(voucher_id, value, voucher_type) VALUES(UUID_TO_BIN(?), ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM vouchers";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String UPDATE_QUERY = "UPDATE vouchers SET value = ?, voucher_type = ? WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String TRUNCATE_TABLE = "DELETE FROM vouchers";

    private static final RowMapper<Voucher> ROW_MAPPER = (resultSet, rowNum) -> {
        UUID voucherId = UUIDConverter.toUUID(resultSet.getBytes("voucher_id"));
        long value = resultSet.getLong("value");
        int voucherType = resultSet.getInt("voucher_type");
        return VoucherType.of(voucherType, voucherId, value);
    };

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Voucher save(Voucher voucher) {
        jdbcTemplate.update(INSERT_QUERY, voucher.getVoucherId().toString().getBytes(), voucher.getValue(), VoucherType.predictVoucherType(voucher));
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherID) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, ROW_MAPPER, voucherID.toString().getBytes()));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Voucher voucher) {
        jdbcTemplate.update(UPDATE_QUERY,
                voucher.getValue(),
                VoucherType.predictVoucherType(voucher),
                voucher.getVoucherId().toString().getBytes());
    }

    @Override
    public void delete(Voucher voucher) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, voucher.getVoucherId().toString().getBytes());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, ROW_MAPPER);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(TRUNCATE_TABLE);
    }
}
