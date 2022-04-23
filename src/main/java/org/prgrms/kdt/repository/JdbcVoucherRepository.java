package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static org.prgrms.kdt.repository.CustomerJdbcRepository.toUUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JdbcWalletRepository jdbcWalletRepository;

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

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate, JdbcWalletRepository jdbcWalletRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcWalletRepository = jdbcWalletRepository;
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
    public Voucher getByVoucherId(UUID voucherId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
        }};
        return jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                paramMap, voucherRowMapper);
    }

    @Override
    public List<Voucher> getVoucherListOwnerIdIsEmpty() {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE owner_id IS NULL", voucherRowMapper);
    }

    @Override
    public Voucher updateVoucherOwner(UUID voucherId, UUID customerId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("customerId", customerId.toString().getBytes());
            put("ownedAt", LocalDateTime.now());
        }};
        jdbcTemplate.update("UPDATE vouchers SET owner_id = UUID_TO_BIN(:customerId), owned_time = :ownedAt WHERE voucher_id = UUID_TO_BIN(:voucherId)", paramMap);

        return jdbcWalletRepository.selectJoinVoucherCustomer(voucherId);
    }

    @Override
    public Voucher getByVoucherNotProvided(UUID voucherId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
        }};
        return jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId) AND owner_id IS NULL"
        ,paramMap, voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }


}
