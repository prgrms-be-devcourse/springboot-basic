package org.prgrms.springbootbasic.repository.voucher;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.prgrms.springbootbasic.controller.VoucherType.FIXED;
import static org.prgrms.springbootbasic.controller.VoucherType.PERCENT;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.GOT_EMPTY_RESULT_MSG;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.NOTHING_WAS_DELETED_EXP_MSG;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.NOTHING_WAS_INSERTED_EXP_MSG;
import static org.prgrms.springbootbasic.repository.DBErrorMsg.NOTING_WAS_UPDATED_EXP_MSG;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springbootbasic.entity.customer.Customer;
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

    //SQL
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String SELECT_BY_CUSTOMER_SQL = "SELECT * FROM vouchers WHERE customer_id = uuid_to_bin(?)";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = uuid_to_bin(?)";
    private static final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, type, amount, percent) VALUES (uuid_to_bin(?), ?, ?, ?)";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";
    private static final String DELETE_BY_VOUCHER_ID_SQL = "DELETE FROM vouchers WHERE voucher_id = uuid_to_bin(?)";
    private static final String UPDATE_CUSTOMER_ID_SQL = "UPDATE vouchers SET customer_id = uuid_to_bin(?) WHERE voucher_id = uuid_to_bin(?)";

    //Column
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_VOUCHER_ID = "voucher_id";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_PERCENT = "percent";
    private static final String COLUMN_CUSTOMER_ID = "customer_id";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> mapToVoucher = (resultSet, i) -> {
        var type = resultSet.getString(COLUMN_TYPE);
        var voucherId = toUUID(resultSet.getBytes(COLUMN_VOUCHER_ID));
        var customerId = resultSet.getBytes(COLUMN_CUSTOMER_ID) != null ?
            toUUID(resultSet.getBytes(COLUMN_CUSTOMER_ID)) : null;
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

        if (voucher.isFixed()) {
            var insert = jdbcTemplate.update(
                INSERT_SQL,
                voucher.getVoucherId().toString().getBytes(UTF_8),
                FIXED.toString(),
                ((FixedAmountVoucher) voucher).getAmount(),
                null);
            if (insert != 1) {
                throw new RuntimeException(NOTHING_WAS_INSERTED_EXP_MSG.getMessage());
            }
            return;
        }
        if (voucher.isPercent()) {
            var insert = jdbcTemplate.update(
                INSERT_SQL,
                voucher.getVoucherId().toString().getBytes(UTF_8),
                PERCENT.toString(),
                null,
                ((PercentDiscountVoucher) voucher).getPercent());
            if (insert != 1) {
                throw new RuntimeException(NOTHING_WAS_INSERTED_EXP_MSG.getMessage());
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
                id.toString().getBytes(UTF_8)));
        } catch (EmptyResultDataAccessException e) {
            logger.info(GOT_EMPTY_RESULT_MSG.getMessage());
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
            UPDATE_CUSTOMER_ID_SQL,
            voucher.getCustomerId().toString().getBytes(UTF_8),
            voucher.getVoucherId().toString().getBytes(UTF_8));
        if (update != 1) {
            throw new RuntimeException(NOTING_WAS_UPDATED_EXP_MSG.getMessage());
        }
        return voucher;
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        logger.info("findByCustomer() called");

        return jdbcTemplate.query(SELECT_BY_CUSTOMER_SQL,
            mapToVoucher,
            customer.getCustomerId().toString().getBytes(UTF_8));
    }

    public void deleteVoucher(Voucher voucher) {
        logger.info("deleteVoucher() called");

        var delete = jdbcTemplate.update(DELETE_BY_VOUCHER_ID_SQL,
            voucher.getVoucherId().toString().getBytes(UTF_8));
        if (delete != 1) {
            throw new RuntimeException(NOTHING_WAS_DELETED_EXP_MSG.getMessage());
        }
    }
}
