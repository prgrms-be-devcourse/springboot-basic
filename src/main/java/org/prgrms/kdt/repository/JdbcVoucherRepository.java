package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.repository.CustomerJdbcRepository.toUUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        var discountAmount = resultSet.getInt("discount_amount");
        var voucherType = resultSet.getInt("voucher_type");
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        if (voucherType == 1) {
            return new FixedAmountVoucher(voucherId, discountAmount, createdAt);
        }
        return new PercentDiscountVoucher(voucherId, discountAmount, createdAt);
    };

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {

        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", voucher.getVoucherType());
            put("discountAmount", voucher.getDiscountAmount());
            put("createdAt", voucher.getCreateAt());
        }};
        jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, discount_amount, created_at) VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discountAmount, :createdAt)", paramMap);
        return voucher;
    }

//    public Voucher insertWithOwner(Voucher voucher, Customer customer) {
//
//        var paramMap = new HashMap<String, Object>() {{
//            put("voucherId", voucher.getVoucherId().toString().getBytes());
//            put("voucherType", voucher.getVoucherType());
//            put("discountAmount", voucher.getDiscountAmount());
//            put("createdAt", voucher.getCreateAt());
//            put("ownedAt", voucher.getOwnedAt());
//            put("ownerId", customer.getCustomerId().toString().getBytes());
//        }};
//        jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, discount_amount, created_at, owner_id, owned_time) VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discountAmount, :createdAt, :ownedAt, :ownerId)", paramMap);
//        return voucher;
//    }

    @Override
    public Map getVoucherList() {
        var receivedVoucherList = jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
        Map<UUID, Voucher> voucherList = new HashMap<>();
        for (Voucher voucher : receivedVoucherList) {
            voucherList.put(voucher.getVoucherId(), voucher);
        }
        return voucherList;
    }

    @Override
    public Voucher delete(Voucher voucher) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
        }};
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)", paramMap);
        return voucher;
    }

    @Override
    public Optional<Voucher> getByVoucherId(UUID voucherId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
        }};
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                paramMap, voucherRowMapper));
    }
}
