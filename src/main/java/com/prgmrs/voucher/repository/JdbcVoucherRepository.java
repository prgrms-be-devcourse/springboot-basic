package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.enums.DiscountType;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.DiscountStrategy;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.vo.Amount;
import com.prgmrs.voucher.model.vo.Percent;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {
        String sql = "INSERT INTO voucher (voucher_id, discount_type, discount_value) VALUES (:voucherId, :discountType, :discountValue)";

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("voucherId", voucher.getVoucherId().toString());

        if (voucher.getDiscountStrategy() instanceof FixedAmountDiscountStrategy fixedAmountDiscountStrategy) {
            short discountType = DiscountType.fromEnumValue("FIXED_AMOUNT_DISCOUNT");
            paramMap.put("discountType", discountType);
            paramMap.put("discountValue", fixedAmountDiscountStrategy.getAmount().getValue());
        }

        if (voucher.getDiscountStrategy() instanceof PercentDiscountStrategy percentDiscountStrategy) {
            short discountType = DiscountType.fromEnumValue("PERCENT_DISCOUNT");
            paramMap.put("discountType", discountType);
            paramMap.put("discountValue", percentDiscountStrategy.getPercent().getValue());
        }

        jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT voucher_id, discount_type, discount_value FROM voucher";

        List<Voucher> voucherList = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, Collections.emptyMap());
        for (Map<String, Object> row : rows) {
            UUID voucherId = UUID.fromString(row.get("voucher_id").toString());
            Number discountTypeNumber = (Number) row.get("discount_type");
            short discountTypeShort = discountTypeNumber.shortValue();
            DiscountType discountTypeEnum = DiscountType.fromValue(discountTypeShort);
            long discountValue = (long) row.get("discount_value");
            DiscountStrategy discountStrategy;

            switch(discountTypeEnum) {
                case FIXED_AMOUNT_DISCOUNT -> {
                    Amount amount = new Amount(discountValue);
                    discountStrategy = new FixedAmountDiscountStrategy(amount);
                }
                case PERCENT_DISCOUNT -> {
                    Percent percent = new Percent(discountValue);
                    discountStrategy = new PercentDiscountStrategy(percent);
                }
                default -> throw new NoSuchVoucherTypeException("no such discount type");
            }

            Voucher voucher = new Voucher(voucherId, discountStrategy);

            voucherList.add(voucher);
        }

        return voucherList;
    }

    @Override
    public List<Voucher> findByUsername() {
        return null;
    }
}
