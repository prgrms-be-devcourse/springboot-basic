package com.pppp0722.vouchermanagement.voucher.repository;

import static com.pppp0722.vouchermanagement.util.JdbcUtils.toLocalDateTime;
import static com.pppp0722.vouchermanagement.util.JdbcUtils.toUUID;
import static com.pppp0722.vouchermanagement.voucher.model.VoucherType.FIXED_AMOUNT;
import static com.pppp0722.vouchermanagement.voucher.model.VoucherType.getVoucherType;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public Optional<Voucher> insert(Voucher voucher) {
        try {
            int update = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, type, amount, created_at, member_id) VALUES(UNHEX(REPLACE(:voucherId, '-', '')), :type, :amount, :createdAt, UNHEX(REPLACE(:memberId, '-', '')))",
                toParamMap(voucher));

            if (update != 1) {
                logger.error("Nothing was created! (Voucher)");
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            logger.error("Can not create voucher!", e);
            return Optional.empty();
        }
        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers;
        try {
            vouchers = jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
        } catch (RuntimeException e) {
            logger.error("Can not read vouchers!", e);
            vouchers = new ArrayList<>();
        }
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (RuntimeException e) {
            logger.error("Can not read voucher!", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByMemberId(UUID memberId) {
        List<Voucher> vouchers = jdbcTemplate.query(
            "SELECT * FROM vouchers WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
            Collections.singletonMap("memberId", memberId.toString().getBytes()),
            voucherRowMapper);

        return vouchers;
    }

    @Override
    public Optional<Voucher> update(Voucher voucher) {
        try {
            int update = jdbcTemplate.update(
                "UPDATE vouchers SET type = :type, amount = :amount WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                toParamMap(voucher));

            if (update != 1) {
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            logger.error("Can not update voucher!", e);
            return Optional.empty();
        }

        return Optional.of(voucher);
    }

    @Override
    public Optional<Voucher> delete(Voucher voucher) {
        try {
            int update = jdbcTemplate.update(
                "DELETE FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                toParamMap(voucher));

            if (update != 1) {
                return Optional.empty();
            }
        } catch (RuntimeException e) {
            logger.error("Can not delete voucher!", e);
            return Optional.empty();
        }

        return Optional.of(voucher);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType type = getVoucherType(resultSet.getString("type"));
        long amount = resultSet.getLong("amount");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("createdAt"));
        UUID memberId = toUUID(resultSet.getBytes("member_id"));

        if (type.equals(FIXED_AMOUNT)) {
            return new FixedAmountVoucher(voucherId, amount, createdAt, memberId);
        } else {
            return new PercentDiscountVoucher(voucherId, amount, createdAt, memberId);
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
