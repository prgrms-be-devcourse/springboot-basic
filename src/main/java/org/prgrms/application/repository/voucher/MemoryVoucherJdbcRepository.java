package org.prgrms.application.repository.voucher;

import org.prgrms.application.domain.voucher.FixedAmountVoucher;
import org.prgrms.application.domain.voucher.PercentAmountVoucher;
import org.prgrms.application.domain.voucher.Voucher;
import org.prgrms.application.repository.customer.CustomerJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);
    private static final int HAS_UPDATE = 1;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Map<Long, Voucher> storage = new ConcurrentHashMap<>();

    public MemoryVoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        Long voucherId = resultSet.getLong("voucher_id");
        String voucherType = resultSet.getString("voucherType");
        Double percentAmount = resultSet.getDouble("percent_amount");

        Voucher voucher;

        switch (voucherType) {
            case "fixed":
                Double fixedAmount = resultSet.getDouble("fixed_amount");
                voucher = new FixedAmountVoucher(voucherId, fixedAmount);
                break;
            case "percent":
                Double percent = resultSet.getDouble("percent");
                voucher = new PercentAmountVoucher(voucherId, percentAmount);
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
            put("voucherType", voucher.getVoucherType());

            switch (voucher.getVoucherType()){
                case FIXED:
                    FixedAmountVoucher fixedAmountVoucher = (FixedAmountVoucher) voucher;
                    put("fixedAmount",fixedAmountVoucher.getFixedAmount());
                    break;
                case PERCENT:
                    PercentAmountVoucher percentAmountVoucher = (PercentAmountVoucher) voucher;
                    put("percentAmount",percentAmountVoucher.getPercentAmount());
            }
    }};}

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
        int update = jdbcTemplate.update("UPDATE vouchers SET vouchers(fixed_amount,percent_amount VALUES (:fixed_amount,:percent_amount", toParamMap(voucher));
        if( update != HAS_UPDATE){
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
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM vouchers", Collections.emptyMap());
    }
}
