package org.prgrms.application.repository.voucher;

import org.prgrms.application.domain.voucher.FixedAmountVoucher;
import org.prgrms.application.domain.voucher.PercentAmountVoucher;
import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.repository.customer.CustomerJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.application.domain.voucher.VoucherType.FIXED;
import static org.prgrms.application.domain.voucher.VoucherType.PERCENT;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private static final int HAS_UPDATE = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        Long voucherId = resultSet.getLong("voucher_id");
        String voucherType = resultSet.getString("voucher_type");

        Voucher voucher;

        switch (voucherType) {
            case "FIXED":
                Double fixedAmount = resultSet.getDouble("fixed_amount");
                voucher = new FixedAmountVoucher(voucherId, FIXED, fixedAmount);
                break;
            case "PERCENT":
                Double percentAmount = resultSet.getDouble("percent_amount");
                voucher = new PercentAmountVoucher(voucherId, PERCENT, percentAmount);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 voucher타입입니다." + voucherType);
        }
        return voucher;
    };

    // 맵의 키를 파라미터로 변경
    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId());

            switch (voucher.getVoucherType()) {
                case FIXED:
                    FixedAmountVoucher fixedAmountVoucher = (FixedAmountVoucher) voucher;
                    put("voucherType", FIXED.toString());
                    put("fixedAmount", fixedAmountVoucher.getFixedAmount());
                    put("percentAmount", null);
                    break;
                case PERCENT:
                    PercentAmountVoucher percentAmountVoucher = (PercentAmountVoucher) voucher;
                    put("voucherType", PERCENT.toString());
                    put("fixedAmount", null);
                    put("percentAmount", percentAmountVoucher.getPercentAmount());
            }
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, fixed_amount, percent_amount) " +
                        "VALUES (:voucherId, :voucherType, :fixedAmount, :percentAmount)",
                toParamMap(voucher));
        if (update != HAS_UPDATE) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update("UPDATE vouchers SET fixed_amount = :fixedAmount, percent_amount = :percentAmount WHERE voucher_id = :voucherId",
                toParamMap(voucher));
        if (update != HAS_UPDATE) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = :voucherId",
                    Collections.singletonMap("voucherId", voucherId),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Voucher>> findByType(VoucherType voucherType) {
        try {
            List<Voucher> vouchers = jdbcTemplate.query("select * from vouchers WHERE voucher_type = :voucherType",
                    Collections.singletonMap("voucherType", voucherType.name()),
                    voucherRowMapper);
            return Optional.ofNullable(vouchers);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }
}
