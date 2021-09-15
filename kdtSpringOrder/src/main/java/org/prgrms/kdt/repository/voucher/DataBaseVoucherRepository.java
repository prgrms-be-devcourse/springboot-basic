package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"prod", "default"})
@Primary
public class DataBaseVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet, "customer_id");
        UUID voucherId = toUUID(resultSet, "voucher_id");
        int discount = resultSet.getInt("discount");
        String voucherType = resultSet.getString("voucher_type");
        if(VoucherType.getVoucherType(voucherType) == VoucherType.FIXED) {
            return new FixedAmountVoucher(customerId, voucherId, discount, VoucherType.FIXED);
        } else {
            return new PercentDiscountVoucher(customerId, voucherId, discount, VoucherType.DISCOUNT);
        }
    };

    public DataBaseVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<String, Object>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("customerId", voucher.getCustomerId().toString().getBytes());
            put("voucherType", voucher.getVoucherType().toString());
            put("discount", voucher.getDiscount());
            put("createdAt", Timestamp.valueOf(LocalDateTime.now()));
        }};
    }

    @Override
    public Optional<Voucher> save(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, customer_id, voucher_type, discount, created_at) VALUES (UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId), :voucherType, :discount, :createdAt)", toParamMap(voucher));
        if(update != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return Optional.of(voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByVoucherId(UUID customerId) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE customer_id = UUID_TO_BIN(:customerId)", Collections.singletonMap("customerId", customerId.toString().getBytes()), voucherRowMapper);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public void deleteVoucher(UUID voucherId, UUID customerId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("voucherId", voucherId.toString().getBytes());
        paramMap.put("customerId", customerId.toString().getBytes());
        jdbcTemplate.update("DELETE FROM vouchers WHERE customer_id = UUID_TO_BIN(:customerId) AND voucher_id = UUID_TO_BIN(:voucherId)", paramMap);
    }

    static private UUID toUUID(ResultSet resultSet, String columnLabel) throws SQLException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(resultSet.getBytes(columnLabel));
        UUID uuid = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return uuid;
    }
}
