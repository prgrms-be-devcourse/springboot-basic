package org.prgrms.springbootbasic.repository;

import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.type.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.prgrms.springbootbasic.util.UUIDUtil.toUUID;


@Primary
@Repository
public class DbVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(DbVoucherRepository.class);
    public static final String SELECT_SQL = "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)";

    private static final String INSERT_SQL = "insert into vouchers(voucher_id, voucher_type, discount_quantity, voucher_count, discount_ratio, created_at, ended_at) " +
            "values (UUID_TO_BIN(:voucherId), :voucherType, :discountQuantity, :voucherCount, :discountRatio, :createdAt, :endedAt)";

    public static final String SELECT_ALL_SQL = "select * from vouchers";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    DbVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParaMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes(UTF_8));
            put("voucherType", voucher.getVoucherType().name());
            put("discountQuantity", voucher.getDiscountQuantity());
            put("discountRatio", voucher.getDiscountRatio());
            put("voucherCount", voucher.getVoucherCount());
            put("createdAt", voucher.getCreatedAt());
            put("endedAt", voucher.getEndedAt());
        }};
    }

    private static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String voucherType = resultSet.getString("voucher_type");
        long discountQuantity = resultSet.getLong("discount_quantity");
        long discountRatio = resultSet.getLong("discount_ratio");
        long voucherCount = resultSet.getLong("voucher_count");
        LocalDate createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().toLocalDate();
        LocalDate endedAt = resultSet.getTimestamp("ended_at").toLocalDateTime().toLocalDate();

        return Voucher.builder()
                .voucherId(voucherId)
                .voucherType(VoucherType.valueOf(voucherType))
                .discountQuantity(discountQuantity)
                .discountRatio(discountRatio)
                .voucherCount(voucherCount)
                .createdAt(createdAt)
                .endedAt(endedAt)
                .build();
    };

    @Override
    public void insert(Voucher voucher) {
        int update = jdbcTemplate.update(INSERT_SQL, toParaMap(voucher));
        if (update != 1) {
            logger.error("Nothing was inserted");
            throw new RuntimeException("Nothing was inserted");
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_SQL,
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

}
