package com.pppp0722.vouchermanagement.voucher.repository;

import static com.pppp0722.vouchermanagement.util.JdbcUtils.toLocalDateTime;
import static com.pppp0722.vouchermanagement.util.JdbcUtils.toUUID;

import com.pppp0722.vouchermanagement.exception.InvalidVoucherTypeException;
import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try {
            int update = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, type, amount, created_at, member_id) VALUES(UNHEX(REPLACE(:voucherId, '-', '')), :type, :amount, :createdAt, UNHEX(REPLACE(:memberId, '-', '')))",
                toParamMap(voucher));

            if (update != 1) {
                throw new RuntimeException("Nothing gets inserted!");
            }

            return voucher;
        } catch (RuntimeException e) {
            logger.error("Failed to insert voucher!", e);
            throw e;
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
        } catch (RuntimeException e) {
            logger.error("Failed to find all vouchers!", e);
            throw e;
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Failed to find voucher by voucher id!", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByMemberId(UUID memberId) {
        try {
            return jdbcTemplate.query(
                "SELECT * FROM vouchers WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
                Collections.singletonMap("memberId", memberId.toString().getBytes()),
                voucherRowMapper);

        } catch (RuntimeException e) {
            logger.error("Failed to find voucher by member id!", e);
            throw e;
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        try {
            int update = jdbcTemplate.update(
                "UPDATE vouchers SET type = :type, amount = :amount WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                toParamMap(voucher));

            if (update != 1) {
                throw new RuntimeException("Nothing gets updated!");
            }

            return voucher;
        } catch (RuntimeException e) {
            logger.error("Failed to update voucher!", e);
            throw e;
        }
    }

    @Override
    public Voucher delete(Voucher voucher) {
        try {
            int update = jdbcTemplate.update(
                "DELETE FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                toParamMap(voucher));

            if (update != 1) {
                throw new RuntimeException("Nothing gets deleted!");
            }

            return voucher;
        } catch (RuntimeException e) {
            logger.error("Failed to delete voucher!", e);
            throw e;
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
        } catch (RuntimeException e) {
            logger.error("Failed to delete all vouchers!", e);
            throw e;
        }
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType type = VoucherType.valueOf(resultSet.getString("type"));
        long amount = resultSet.getLong("amount");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        UUID memberId = toUUID(resultSet.getBytes("member_id"));

        switch (type) {
            case FIXED_AMOUNT:
                return new FixedAmountVoucher(voucherId, amount, createdAt, memberId);
            case PERCENT_DISCOUNT:
                return new PercentDiscountVoucher(voucherId, amount, createdAt, memberId);
            default:
                logger.error("Invalid voucher type!");
                throw new InvalidVoucherTypeException("Invalid voucher type!");
        }
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("type", voucher.getType().toString());
        paramMap.put("amount", voucher.getAmount());
        paramMap.put("createdAt", voucher.getCreatedAt());
        paramMap.put("memberId", voucher.getMemberId().toString().getBytes());

        return paramMap;
    }
}
