package com.programmers.part1.order.voucher.repository;

import com.programmers.part1.domain.voucher.FixedAmountVoucher;
import com.programmers.part1.domain.voucher.PercentAmountVoucher;
import com.programmers.part1.domain.voucher.Voucher;
import com.programmers.part1.domain.voucher.VoucherType;
import com.programmers.part1.exception.NoUpdateException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.part1.util.JdbcUtil.toLocalDateTime;
import static com.programmers.part1.util.JdbcUtil.toUUID;

@Repository
@Primary
public class VoucherJdbcRepository implements VoucherRepository<UUID, Voucher> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id,voucher_type,voucher_amount, created_at) VALUES(UUID_TO_BIN(:voucherId),:voucherType,:amount,:createdAt)",
                toParamMap(voucher));
        if (update != 1)
            throw new NoUpdateException("바우처의 저장에 실패했습니다.");
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public List<Voucher> findVouchersByVoucherType(VoucherType voucherType) {
        return jdbcTemplate.query("SELECT * FROM vouchers WHERE voucher_type = :voucherType",
                Collections.singletonMap("voucherType", voucherType.toString()),
                voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                        Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                        voucherRowMapper)
        );
    }

    @Override
    public List<Voucher> findVoucherByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT v.voucher_id, v.voucher_type, v.voucher_amount, v.created_at FROM voucher_wallets AS vw JOIN vouchers AS v ON vw.voucher_id = v.voucher_id WHERE customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherRowMapper);

    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update("UPDATE vouchers SET voucher_type = :voucherType, voucher_amount = :amount WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucher));

        if (update != 1)
            throw new NoUpdateException("바우처의 수정에 실패했습니다.");

        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        int update = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));

        if (update != 1)
            throw new NoUpdateException("바우처 삭제에 실패했습니다.");

    }

    @Override
    public void deleteAll() {
        int update = jdbcTemplate.update("DELETE * FROM vouchers",
                Collections.emptyMap());

        if (update != 1)
            throw new NoUpdateException("전체 바우처 삭제에 실패했습니다.");
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<String, Object>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", voucher.getVoucherType().toString());
            put("amount", voucher.getAmount());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
        }};
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        int amount = resultSet.getInt("voucher_amount");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));

        if (voucherType == VoucherType.FIXED)
            return new FixedAmountVoucher(voucherId, amount, createdAt);
        else if (voucherType == VoucherType.PERCENT)
            return new PercentAmountVoucher(voucherId, amount, createdAt);
        else
            throw new RuntimeException();
    };
}
