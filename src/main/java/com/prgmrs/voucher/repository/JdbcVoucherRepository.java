package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.enums.DiscountType;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.DiscountStrategy;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.model.wrapper.Amount;
import com.prgmrs.voucher.model.wrapper.Percent;
import com.prgmrs.voucher.model.wrapper.Username;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Voucher> toRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
            long discountValue = rs.getLong("discount_value");

            DiscountStrategy discountStrategy;
            short discountTypeShort = rs.getShort("discount_type");
            DiscountType discountTypeEnum = DiscountType.fromShort(discountTypeShort);

            switch (discountTypeEnum) {
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

            return new Voucher(voucherId, discountStrategy);
        };
    }

    @Override
    public void save(Voucher voucher) {
        String sql = "INSERT INTO voucher (voucher_id, discount_type, discount_value) VALUES (:voucherId, :discountType, :discountValue)";

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("voucherId", voucher.voucherId().toString());

        if (voucher.discountStrategy() instanceof FixedAmountDiscountStrategy fixedAmountDiscountStrategy) {
            short discountType = DiscountType.fromEnumValueStringToShortValue("FIXED_AMOUNT_DISCOUNT");
            paramMap.put("discountType", discountType);
            paramMap.put("discountValue", fixedAmountDiscountStrategy.amount().value());
        }

        if (voucher.discountStrategy() instanceof PercentDiscountStrategy percentDiscountStrategy) {
            short discountType = DiscountType.fromEnumValueStringToShortValue("PERCENT_DISCOUNT");
            paramMap.put("discountType", discountType);
            paramMap.put("discountValue", percentDiscountStrategy.percent().value());
        }

        jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT voucher_id, discount_type, discount_value FROM voucher ORDER BY created_at";

        return jdbcTemplate.query(sql, toRowMapper());
    }

    @Override
    public List<Voucher> getAssignedVoucherListByUsername(Username username) {
        String sql = """
                         SELECT
                           v.voucher_id, v.discount_type, v.discount_value
                         FROM `voucher` v
                           INNER JOIN `wallet` w ON v.voucher_id = w.voucher_id
                               INNER JOIN `user` u ON w.user_id = u.user_id
                         WHERE  u.username = :username
                           AND w.unassigned_time IS NULL
                               ORDER BY v.created_at
                """;

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("username", username.value());

        return jdbcTemplate.query(sql, paramMap, toRowMapper());
    }

    @Override
    public List<Voucher> getAssignedVoucherList() {
        String sql = """
                       SELECT
                           v.voucher_id, v.discount_type, v.discount_value
                       FROM `voucher` v
                           INNER JOIN `wallet` w ON v.voucher_id = w.voucher_id
                       WHERE w.unassigned_time IS NULL
                           ORDER BY v.created_at
                """;

        return jdbcTemplate.query(sql, toRowMapper());
    }

    @Override
    public List<Voucher> getNotAssignedVoucherList() {
        String sql = """
                    SELECT
                        DISTINCT v.voucher_id, v.discount_type, v.discount_value
                    FROM `voucher` v
                        LEFT JOIN `wallet` w ON v.voucher_id = w.voucher_id
                    WHERE w.voucher_id IS NULL
                        OR (v.voucher_id IN (SELECT
                                                w.voucher_id
                                             FROM `wallet` w
                                                GROUP BY w.voucher_id
                                                HAVING MAX(w.is_used) = 0
                                                    AND MAX(CASE WHEN w.unassigned_time
                                                                IS NULL THEN 1 ELSE 0 END) = 0
                                            )
                            )
                """;

        return jdbcTemplate.query(sql, toRowMapper());
    }

    @Override
    public List<Voucher> findByCreationTimeAndDiscountType(LocalDateTime startDate, LocalDateTime endDate, short discountType) {
        String sql = """
                       SELECT
                           v.voucher_id, v.discount_type, v.discount_value
                       FROM `voucher` v
                       WHERE (v.discount_type = :discountType) 
                            AND (v.created_at BETWEEN :startDate AND :endDate)
                """;

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        paramMap.put("discountType", discountType);

        return jdbcTemplate.query(sql, paramMap, toRowMapper());
    }

    @Override
    public int removeVoucher(UUID voucherUUID) {
        String sql = "DELETE FROM `voucher` v WHERE v.voucher_id = :voucherId";

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("voucherId", voucherUUID.toString());

        return jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public Voucher findById(UUID voucherUUID) {
        String sql = """
                       SELECT
                           v.voucher_id, v.discount_type, v.discount_value
                       FROM `voucher` v
                       WHERE v.voucher_id = :voucherId
                """;

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("voucherId", voucherUUID.toString());

        return jdbcTemplate.queryForObject(sql, paramMap, toRowMapper());
    }
}
