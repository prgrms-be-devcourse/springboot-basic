package org.prgrms.voucherprgrms.voucher.repository;

import org.prgrms.voucherprgrms.voucher.model.FixedAmountVoucher;
import org.prgrms.voucherprgrms.voucher.model.PercentDiscountVoucher;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
@Qualifier("named")
public class VoucherNamedJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherNamedJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherNamedJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long value = resultSet.getLong("value");
        String DTYPE = resultSet.getString("DTYPE");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        VoucherType type = VoucherType.getType(DTYPE);
        switch (type) {
            case FIXEDAMOUNT:
                return new FixedAmountVoucher(voucherId, value, createdAt);
            case PERCENTDISCOUNT:
                return new PercentDiscountVoucher(voucherId, value, createdAt);
            default:
                throw new IllegalArgumentException("Voucher type error");
        }
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {
            {
                put("voucherId", voucher.getVoucherId().toString().getBytes());
                put("value", voucher.getValue());
                put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
                put("DTYPE", voucher.getDTYPE());
            }
        };
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var insert = jdbcTemplate.update("insert into VOUCHERS(voucher_id, value, created_at, DTYPE) values(UUID_TO_BIN(:voucherId), :value, :createdAt, :DTYPE);",
                toParamMap(voucher));

        if (insert != 1) {
            throw new DuplicateKeyException("Voucher Insertion failed");
        }

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from VOUCHERS where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result by UUID : {}", voucherId, e);
            return Optional.empty();
        }
    }


    @Override
    public List<Voucher> findByVoucherType(String DTYPE) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("DTYPE", DTYPE);
        }};
        return jdbcTemplate.query("select * from VOUCHERS where DTYPE = :DTYPE", paramMap, voucherRowMapper);
    }

    public List<Voucher> findByCreated(LocalDateTime start, LocalDateTime end) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("start", Timestamp.valueOf(start));
            put("end", Timestamp.valueOf(end.plusDays(1)));
        }};

        return jdbcTemplate.query("select * from VOUCHERS where created_at between date(:start) and date(:end)", paramMap, voucherRowMapper);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from VOUCHERS", voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from VOUCHERS", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        Map<String, Object> paramMap = Collections.singletonMap("voucherId", voucherId.toString().getBytes());

        var delete = jdbcTemplate.update("delete from VOUCHERS where voucher_id = UUID_TO_BIN(:voucherId)", paramMap);
        if (delete != 1) {
            throw new IllegalArgumentException();
        }
    }

    private static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
