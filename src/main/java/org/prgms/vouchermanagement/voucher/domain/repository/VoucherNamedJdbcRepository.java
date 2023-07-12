package org.prgms.vouchermanagement.voucher.domain.repository;

import org.prgms.vouchermanagement.global.constant.ExceptionMessageConstant;
import org.prgms.vouchermanagement.voucher.VoucherType;
import org.prgms.vouchermanagement.voucher.domain.entity.Voucher;
import org.prgms.vouchermanagement.voucher.exception.VoucherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Profile("jdbc")
public class VoucherNamedJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherNamedJdbcRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("discountAmount", voucher.getDiscount());
            put("voucherType", voucher.getVoucherType() == VoucherType.FIXED_AMOUNT_VOUCHER_TYPE ? 1 : 2);
        }};
    }

    private RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long discountAmount = resultSet.getLong("discount_amount");
        VoucherType voucherType = VoucherType.getVoucherType(resultSet.getInt("voucher_type"));

        return switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER_TYPE -> new Voucher(voucherId, discountAmount, VoucherType.FIXED_AMOUNT_VOUCHER_TYPE);
            case PERCENT_DISCOUNT_VOUCHER_TYPE -> new Voucher(voucherId, discountAmount, VoucherType.PERCENT_DISCOUNT_VOUCHER_TYPE);
        };
    };

    public VoucherNamedJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                voucherRowMapper));
    }

    @Override
    public Voucher save(Voucher voucher) {
        int updated = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, discount_amount, voucher_type) VALUES(UNHEX(REPLACE(:voucherId, '-', '')), :discountAmount, :voucherType)",
                toParamMap(voucher));
        if (updated != 1) {
            throw new VoucherException(ExceptionMessageConstant.VOUCHER_NOT_INSERTED_EXCEPTION);
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcTemplate.update("UPDATE vouchers SET discount_amount = :discountAmount, voucher_type = :voucherType WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                toParamMap(voucher));
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    private UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
