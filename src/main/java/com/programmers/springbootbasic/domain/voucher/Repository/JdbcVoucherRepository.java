package com.programmers.springbootbasic.domain.voucher.Repository;

import com.programmers.springbootbasic.common.domain.Page;
import com.programmers.springbootbasic.common.domain.Pageable;
import com.programmers.springbootbasic.domain.voucher.Voucher;
import com.programmers.springbootbasic.domain.voucher.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String FAIL_TO_CREATE = "바우처 생성에 실패했습니다. 입력값을 확인해주세요";
    private static final String DATABASE_VOUCHER_ID = "voucher_id";
    private static final String VOUCHER_ID = "voucherId";
    private static final String DATABASE_VOUCHER_TYPE = "voucher_type";
    private static final String VOUCHER_TYPE = "voucherType";
    private static final String DATABASE_AMOUNT_OR_PERCENT = "amount_or_percent";
    private static final String AMOUNT_OR_PERCENT = "amountOrPercent";
    private final RowMapper<Voucher> rowMapper = (rs, rowNum) -> {
        UUID voucherId = UUID.fromString(rs.getString(DATABASE_VOUCHER_ID));
        VoucherType voucherType = VoucherType.valueOf(rs.getString(DATABASE_VOUCHER_TYPE));
        int amountOrPercent = rs.getInt(DATABASE_AMOUNT_OR_PERCENT);
        return Voucher.createVoucher(voucherId, voucherType, amountOrPercent);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID save(Voucher voucher) {
        int affectedRow = jdbcTemplate.update("INSERT INTO voucher(voucher_id, voucher_type, amount_or_percent) VALUES (:voucherId, :voucherType, :amountOrPercent)"
                , toParamMap(voucher));
        if (affectedRow != 1) {
            throw new IllegalArgumentException(FAIL_TO_CREATE);
        }
        return voucher.getVoucherId();
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return Map.of(
                VOUCHER_ID, voucher.getVoucherId().toString(),
                VOUCHER_TYPE, voucher.getVoucherType().name(),
                AMOUNT_OR_PERCENT, voucher.getAmountOrPercent()
        );
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM voucher",
                rowMapper);
    }

    @Override
    public long countTotalVoucher() {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM voucher",
                Collections.emptyMap(),
                Long.class);
    }

    @Override
    public Page<Voucher> findAllWithPagination(Pageable pageable) {
        long voucherCount = countTotalVoucher();
        List<Voucher> vouchers = jdbcTemplate.query(
                "SELECT * FROM voucher LIMIT :size OFFSET :offset",
                Map.of("size", pageable.size(), "offset", pageable.getOffSet())
                , rowMapper);
        return new Page<>(vouchers, pageable, voucherCount);
    }
}
