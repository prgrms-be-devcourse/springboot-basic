package com.prgrms.voucher.repository;

import com.prgrms.exception.NotUpdateException;
import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherCreator;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.DiscountCreator;
import com.prgrms.presentation.message.ErrorMessage;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Primary
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DiscountCreator discountCreator;
    private final VoucherCreator voucherCreator;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate,
            DiscountCreator discountCreator, VoucherCreator voucherCreator) {
        this.jdbcTemplate = jdbcTemplate;
        this.discountCreator = discountCreator;
        this.voucherCreator = voucherCreator;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("voucher_id", voucher.getVoucherId());
        map.put("voucher_type", voucher.getVoucherType().name());
        map.put("discount", voucher.getVoucherDiscount().getDiscountAmount());
        map.put("created_at", Timestamp.valueOf(voucher.getCreatedAt()));

        return map;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, voucher_type, discount) VALUES (:voucher_id, :voucher_type, :discount)",
                toParamMap(voucher));

        if (update != 1) {
            throw new NotUpdateException(ErrorMessage.NOT_UPDATE.getMessage());
        }

        return voucher;
    }

    @Override
    public Vouchers getAllVoucher(VoucherType voucherType, LocalDateTime createdAt) {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT voucher_id, voucher_type, discount, created_at FROM vouchers WHERE deleted = false");
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        if (voucherType != null) {
            queryBuilder.append(" AND voucher_type = :voucherType");
            parameters.addValue("voucherType", voucherType.name());
        }

        if (createdAt != null) {
            queryBuilder.append(" AND created_at >= :created_at");
            parameters.addValue("created_at", createdAt);
        }

        List<Voucher> vouchers = jdbcTemplate.query(
                queryBuilder.toString(),
                parameters,
                getVoucherRowMapper());

        return new Vouchers(vouchers);
    }

    @Override
    public Optional<Voucher> findById(int voucher_id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select voucher_id, voucher_type, discount, created_at from vouchers where voucher_id = :voucher_id",
                    Collections.singletonMap("voucher_id", voucher_id),
                    getVoucherRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            logger.debug(ErrorMessage.NO_RESULT_RETURN_EMPTY.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public int deleteById(int voucherId) {
        int update = jdbcTemplate.update(
                "UPDATE vouchers "
                        + "SET deleted = true "
                        + "WHERE voucher_id = :voucherId",
                Collections.singletonMap("voucherId", voucherId));

        if (update != 1) {
            throw new NotUpdateException(ErrorMessage.NOT_UPDATE.getMessage());
        }

        return voucherId;
    }

    public RowMapper<Voucher> getVoucherRowMapper() {
        return (resultSet, i) -> {
            int voucherId = resultSet.getInt("voucher_id");
            VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
            double discountValue = resultSet.getDouble("discount");
            Discount discount = discountCreator.createDiscount(voucherType, discountValue);
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

            return voucherCreator.createVoucher(voucherId, voucherType, discount, createdAt);
        };
    }

}
