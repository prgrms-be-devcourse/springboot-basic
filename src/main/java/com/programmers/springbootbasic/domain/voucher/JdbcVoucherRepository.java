package com.programmers.springbootbasic.domain.voucher;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String VOUCHER_ID_SNAKE = "voucher_id";
    private static final String VOUCHER_ID_CAMEL = "voucherId";
    private static final String VOUCHER_TYPE_SNAKE = "voucher_type";
    private static final String VOUCHER_TYPE_CAMEL = "voucherType";
    private static final String AMOUNT_OR_PERCENT_SNAKE = "amount_or_percent";
    private static final String AMOUNT_OR_PERCENT_CAMEL = "amountOrPercent";
    private final RowMapper<Voucher> rowMapper = (rs, rowNum) -> {

        UUID voucherId = UUID.fromString(rs.getString(VOUCHER_ID_SNAKE));
        VoucherType voucherType = VoucherType.valueOf(rs.getString(VOUCHER_TYPE_SNAKE));
        int amountOrPercent = rs.getInt(AMOUNT_OR_PERCENT_SNAKE);

        return Voucher.createVoucher(voucherId, voucherType, amountOrPercent);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return Map.of(
                VOUCHER_ID_CAMEL, voucher.getVoucherId().toString(),
                VOUCHER_TYPE_CAMEL, voucher.getVoucherType().name(),
                AMOUNT_OR_PERCENT_CAMEL, voucher.getAmountOrPercent()
        );
    }

    @Override
    public Optional<Voucher> save(Voucher voucher) {
        int affectedRow = jdbcTemplate.update("INSERT INTO voucher(voucher_id, voucher_type, amount_or_percent) VALUES (:voucherId, :voucherType, :amountOrPercent)"
                , toParamMap(voucher));
        if (affectedRow != 1) {
            return Optional.empty();
        }
        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM voucher",
                rowMapper);
    }
}
