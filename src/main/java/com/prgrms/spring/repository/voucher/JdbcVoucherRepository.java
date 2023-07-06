package com.prgrms.spring.repository.voucher;

import com.prgrms.spring.domain.voucher.FixedAmountVoucher;
import com.prgrms.spring.domain.voucher.PercentDiscountVoucher;
import com.prgrms.spring.domain.voucher.Voucher;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Repository
@Primary
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherName = resultSet.getString("voucher_name");
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var discount = resultSet.getInt("discount");
        var discountUnit = resultSet.getString("discount_unit");
        if (voucherName.equals("PercentDiscountVoucher")) {
            return PercentDiscountVoucher.newInstance(voucherId, discount);
        } else {
            return FixedAmountVoucher.newInstance(voucherId, discount);
        }
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherName", voucher.getVoucherName());
            put("discount", voucher.getDiscount());
            put("discountUnit", voucher.getDiscountUnit());
        }};
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO voucher(voucher_id, voucher_name, discount, discount_unit) VALUES (UUID_TO_BIN(:voucherId), :voucherName, :discount, :discountUnit)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from voucher", voucherRowMapper);
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
