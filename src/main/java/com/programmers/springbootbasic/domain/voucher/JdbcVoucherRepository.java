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
    private static final String VOUCHER_ID_FOR_DATABASE = "voucher_id";
    private static final String VOUCHER_ID_FOR_OBJECT = "voucherId";
    private static final String VOUCHER_TYPE_FOR_DATABASE = "voucher_type";
    private static final String VOUCHER_TYPE_FOR_OBJECT = "voucherType";
    private static final String AMOUNT_OR_PERCENT_FOR_DATABASE = "amount_or_percent";
    private static final String AMOUNT_OR_PERCENT_FOR_OBJECT = "amountOrPercent";
    private final RowMapper<Voucher> rowMapper = (rs, rowNum) -> {

        UUID voucherId = UUID.fromString(rs.getString(VOUCHER_ID_FOR_DATABASE));
        VoucherType voucherType = VoucherType.valueOf(rs.getString(VOUCHER_TYPE_FOR_DATABASE));
        int amountOrPercent = rs.getInt(AMOUNT_OR_PERCENT_FOR_DATABASE);

        return Voucher.createVoucher(voucherId, voucherType, amountOrPercent);
    };
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return Map.of(
                VOUCHER_ID_FOR_OBJECT, voucher.getVoucherId().toString(),
                VOUCHER_TYPE_FOR_OBJECT, voucher.getVoucherType().name(),
                AMOUNT_OR_PERCENT_FOR_OBJECT, voucher.getAmountOrPercent()
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
