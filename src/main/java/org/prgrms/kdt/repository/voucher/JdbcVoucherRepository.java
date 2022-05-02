package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.io.OutputConsole;
import org.prgrms.kdt.model.voucher.*;
import org.prgrms.kdt.repository.JdbcWalletRepository;
import org.prgrms.kdt.util.IntUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;


@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JdbcWalletRepository jdbcWalletRepository;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        var discountAmount = resultSet.getInt("discount_amount");
        var voucherType = resultSet.getInt("voucher_type");
        var voucherId = IntUtils.toUUID(resultSet.getBytes("voucher_id"));
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
    public Optional<Voucher> insertVoucher(Voucher voucher) {

        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", voucher.getVoucherType());
            put("discountAmount", voucher.getDiscountAmount());
            put("createdAt", voucher.getCreateAt());
        }};
        var insertInt = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, discount_amount, created_at) " +
                "VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discountAmount, :createdAt)", paramMap);
        if (insertInt > 0) {
            return Optional.of(voucher);
        }
        return Optional.empty();
    }

    @Override
    public VoucherMap getVoucherList() {
        var receivedVoucherList = jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
        Map<UUID, Voucher> vouchers = new HashMap<>();
        for (Voucher voucher : receivedVoucherList) {
            vouchers.put(voucher.getVoucherId(), voucher);
        }
        return new VoucherMap(vouchers);
    }

    @Override
    public void deleteVoucherById(UUID voucherId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
        }};
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)", paramMap);
    }

    @Override
    public Optional<Voucher> getByVoucherId(UUID voucherId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
        }};
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    paramMap, voucherRowMapper));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Vouchers getVoucherListByVoucherType(int voucherType) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherType", voucherType);
        }};
        var receivedVoucherList = jdbcTemplate.query("SELECT * FROM vouchers " +
                "WHERE voucher_type = :voucherType", paramMap, voucherRowMapper);
        return new Vouchers(receivedVoucherList);
    }

    @Override
    public Vouchers getVoucherListByCreatedFromToDate(String fromDate, String toDate) {
        var paramMap = new HashMap<String, Object>() {{
            put("fromDate", fromDate);
            put("toDate", toDate);
        }};
        var receivedVoucherList = jdbcTemplate.query("SELECT * FROM vouchers " +
                "WHERE (created_at BETWEEN :fromDate AND  :toDate)", paramMap, voucherRowMapper);
        return new Vouchers(receivedVoucherList);
    }

    @Override
    public Vouchers getVoucherListOwnerIdIsEmpty() {
        List<Voucher> vouchers= jdbcTemplate.query("SELECT * FROM vouchers WHERE owner_id IS NULL", voucherRowMapper);
        Vouchers voucherList = new Vouchers(vouchers);
        return voucherList;
    }

    @Override
    public Optional<Voucher> updateVoucherOwner(UUID voucherId, UUID customerId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
            put("customerId", customerId.toString().getBytes());
            put("ownedAt", LocalDateTime.now());
        }};
        int returnInt = jdbcTemplate.update("UPDATE vouchers SET owner_id = UUID_TO_BIN(:customerId), " +
                "owned_time = :ownedAt WHERE voucher_id = UUID_TO_BIN(:voucherId)", paramMap);
        if (returnInt > 0) {
            return jdbcWalletRepository.selectJoinVoucherCustomerByVoucherId(voucherId);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Voucher> getVoucherNotProvided(UUID voucherId) {
        var paramMap = new HashMap<String, Object>() {{
            put("voucherId", voucherId.toString().getBytes());
        }};
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId) AND owner_id IS NULL"
                    , paramMap, voucherRowMapper));
        } catch (Exception e) {
            OutputConsole.printMessage("WRONG : invalid input");
            return Optional.empty();
        }
    }

    @Override
    public void deleteAllVouchers() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

}
