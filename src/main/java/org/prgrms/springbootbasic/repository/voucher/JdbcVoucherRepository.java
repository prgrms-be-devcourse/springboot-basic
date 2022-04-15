package org.prgrms.springbootbasic.repository.voucher;

import static org.prgrms.springbootbasic.VoucherType.FIXED;
import static org.prgrms.springbootbasic.VoucherType.PERCENT;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.GOT_EMPTY_RESULT_MSG;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.NOTHING_WAS_INSERTED_EXP_MSG;
import static org.prgrms.springbootbasic.repository.voucher.VoucherDBConstString.COLUMN_AMOUNT;
import static org.prgrms.springbootbasic.repository.voucher.VoucherDBConstString.COLUMN_PERCENT;
import static org.prgrms.springbootbasic.repository.voucher.VoucherDBConstString.COLUMN_TYPE;
import static org.prgrms.springbootbasic.repository.voucher.VoucherDBConstString.COLUMN_VOUCHER_ID;
import static org.prgrms.springbootbasic.repository.voucher.VoucherDBConstString.DELETE_ALL_SQL;
import static org.prgrms.springbootbasic.repository.voucher.VoucherDBConstString.INSERT_SQL;
import static org.prgrms.springbootbasic.repository.voucher.VoucherDBConstString.SELECT_ALL_SQL;
import static org.prgrms.springbootbasic.repository.voucher.VoucherDBConstString.SELECT_BY_ID_SQL;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.voucher.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.voucher.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev2")
public class JdbcVoucherRepository implements VoucherRepository {

    public static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> mapToVoucher = (resultSet, i) -> {
        var type = resultSet.getString(COLUMN_TYPE);
        var voucherId = toUUID(resultSet.getBytes(COLUMN_VOUCHER_ID));
        var customerId = resultSet.getBytes("customer_id") != null ?
            toUUID(resultSet.getBytes("customer_id")) : null;
        if (type.equals(FIXED.toString())) {
            var amount = resultSet.getInt(COLUMN_AMOUNT);
            return new FixedAmountVoucher(voucherId, customerId, amount);
        } else {
            var percent = resultSet.getInt(COLUMN_PERCENT);
            return new PercentDiscountVoucher(voucherId, customerId, percent);
        }
    };

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public void save(Voucher voucher) {
        logger.info("save() called");

        if (voucher instanceof FixedAmountVoucher) {
            var insert = jdbcTemplate.update(
                INSERT_SQL,
                voucher.getVoucherId().toString().getBytes(StandardCharsets.UTF_8),
                FIXED.toString(),
                ((FixedAmountVoucher) voucher).getAmount(),
                null);
            if (insert != 1) {
                throw new RuntimeException(NOTHING_WAS_INSERTED_EXP_MSG);
            }
            return;
        }
        if (voucher instanceof PercentDiscountVoucher) {
            var insert = jdbcTemplate.update(
                INSERT_SQL,
                voucher.getVoucherId().toString().getBytes(StandardCharsets.UTF_8),
                PERCENT.toString(),
                null,
                ((PercentDiscountVoucher) voucher).getPercent());
            if (insert != 1) {
                throw new RuntimeException(NOTHING_WAS_INSERTED_EXP_MSG);
            }
        }
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("findAll() called");

        return jdbcTemplate.query(SELECT_ALL_SQL, mapToVoucher);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        logger.info("findById() called");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                SELECT_BY_ID_SQL,
                mapToVoucher,
                id.toString().getBytes(StandardCharsets.UTF_8)));
        } catch (EmptyResultDataAccessException e) {
            logger.info(GOT_EMPTY_RESULT_MSG);
            return Optional.empty();
        }
    }

    @Override
    public void removeAll() {
        logger.info("removeAll() called");

        jdbcTemplate.update(DELETE_ALL_SQL);
    }

    @Override
    public Voucher updateCustomerId(Voucher voucher) {
        logger.info("updateCustomerId() called");

        var update = jdbcTemplate.update(
            "update vouchers set customer_id = uuid_to_bin(?) where voucher_id = uuid_to_bin(?)",
            voucher.getCustomerId().orElseGet(null).toString().getBytes(StandardCharsets.UTF_8),
            voucher.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
        if (update != 1) {
            throw new RuntimeException("Noting was updated");
        }
        return voucher;
    }

}
