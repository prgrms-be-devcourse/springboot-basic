package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VoucherJdbcTemplateRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcTemplateRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update(
                "insert into vouchers(voucher_id, value) values (UNHEX(REPLACE(?, '-', '')), ?)",
                voucher.getVoucherId(),
                voucher.getValue()
        );
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update(
                "update vouchers set value = ? where voucher_id =UNHEX(REPLACE(?, '-', ''))",
                voucher.getValue(),
                voucher.getVoucherId()
        );
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(
                "select * from vouchers",
                voucherRowMapper
        );
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "select * from vouchers where voucherId = ?",
                voucherRowMapper,
                voucherId
        ));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from customer");
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        //TODO: Voucher  타입을 어떻게 결정할것인가?
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var amount = resultSet.getLong("value");
        var voucherType = VoucherType.getVoucherType(resultSet.getString("type"));
        if (voucherType.equals(VoucherType.FixedAmountVoucher)) {
            logger.info("FIX 타입");
            return new FixedAmountVoucher(voucherId, amount);
        } else {
            logger.info("PER 타입");
            return new PercentDiscountVoucher(voucherId, amount);
        }
    };


    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

}
