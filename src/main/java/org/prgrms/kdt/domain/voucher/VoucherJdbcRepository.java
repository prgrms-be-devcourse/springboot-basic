package org.prgrms.kdt.domain.voucher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String SELECT_BY_FIXED_TYPE_SQL = "SELECT * FROM vouchers WHERE fixed = ?";
    private static final String SELECT_BY_PERCENT_TYPE_SQL = "SELECT * FROM vouchers WHERE percent = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String COUNT_SQL = "SELECT COUNT(*) FROM vouchers";
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, fixed, percent, amount, customer_id) VALUES (UUID_TO_BIN(?), ?, ?, ?, UUID_TO_BIN(?))";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var amount = resultSet.getLong("amount");
        if (resultSet.getBoolean("fixed")) {
            return new FixedAmountVoucher(voucherId, amount);
        } else {
            return new PercentDiscountVoucher(voucherId, amount);
        }
    };
    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                            voucherRowMapper,
                            voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        //TODO: insert(Voucher)
//        var update = jdbcTemplate.update(INSERT_SQL,
//                voucher.getVoucherId().toString().getBytes(),
//                voucher.getAmount()
//        );
//        return update;
        return null;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT_SQL, Integer.class);
    }
}
