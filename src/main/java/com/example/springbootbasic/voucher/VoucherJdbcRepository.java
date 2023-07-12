package com.example.springbootbasic.voucher;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.*;

@Component
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, amount, voucher_type) VALUES (UUID_TO_BIN(:voucherId), :amount, :voucherType)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<VoucherEntity> voucherEntities = jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);

        List<Voucher> result = new ArrayList<>();
        for (VoucherEntity voucherEntity : voucherEntities) {
            String voucherType = voucherEntity.getVoucherType();

            UUID voucherId = voucherEntity.getVoucherId();
            long amount = voucherEntity.getAmount();
            Voucher voucher = switch (voucherType) {
                case "FixedAmountVoucher" -> new FixedAmountVoucher(voucherId, amount);
                case "PercentDiscountVoucher" -> new PercentDiscountVoucher(voucherId, (int) amount);
                default -> throw new IllegalArgumentException("Nothing to find");
            };
            result.add(voucher);
        }

        return result;
    }

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private static final RowMapper<VoucherEntity> voucherRowMapper = (rs, rowNum) -> {
        var voucherId = toUUID(rs.getString("voucher_id").getBytes());
        var amount = rs.getLong("amount");
        var voucherType = rs.getString("voucher_type");
        return new VoucherEntity(voucherId, amount, voucherType);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        var paramMap = new HashMap<String, Object>();
        switch (voucher.getClass().getSimpleName()) {
            case "FixedAmountVoucher" -> {
                FixedAmountVoucher fixedAmountVoucher = (FixedAmountVoucher) voucher;
                paramMap.put("voucherId", fixedAmountVoucher.getVoucherId().toString().getBytes());
                paramMap.put("amount", fixedAmountVoucher.getFixedAmount());
                paramMap.put("voucherType", fixedAmountVoucher.getClass().getSimpleName());
            }
            case "PercentDiscountVoucher" -> {
                PercentDiscountVoucher percentDiscountVoucher = (PercentDiscountVoucher) voucher;
                paramMap.put("voucherId", percentDiscountVoucher.getVoucherId().toString().getBytes());
                paramMap.put("amount", percentDiscountVoucher.getPercent());
                paramMap.put("voucherType", percentDiscountVoucher.getClass().getSimpleName());
            }
        }
        return paramMap;
    }
}
