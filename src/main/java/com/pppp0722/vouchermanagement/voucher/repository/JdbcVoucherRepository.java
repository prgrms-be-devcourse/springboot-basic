package com.pppp0722.vouchermanagement.voucher.repository;

import static com.pppp0722.vouchermanagement.util.Util.toUUID;
import static com.pppp0722.vouchermanagement.voucher.model.VoucherType.FIXED_AMOUNT;
import static com.pppp0722.vouchermanagement.voucher.model.VoucherType.getVoucherType;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
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

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("type", voucher.getType().toString());
            put("amount", voucher.getAmount());
            put("memberId", voucher.getMemberId().toString().getBytes());
        }};
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType type = getVoucherType(resultSet.getString("type"));
        long amount = resultSet.getLong("amount");
        UUID memberId = toUUID(resultSet.getBytes("member_id"));

        if (type.equals(FIXED_AMOUNT)) {
            return new FixedAmountVoucher(voucherId, amount, memberId);
        } else {
            return new PercentDiscountVoucher(voucherId, amount, memberId);
        }
    };

    @Override
    public Optional<Voucher> createVoucher(Voucher voucher) {
        try {
            int update = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, type, amount, member_id) VALUES(UNHEX(REPLACE(:voucherId, '-', '')), :type, :amount, UNHEX(REPLACE(:memberId, '-', '')))",
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
    public List<Voucher> readVouchers() {
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
    public Optional<Voucher> readVoucher(UUID voucherId) {
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
    public List<Voucher> readVouchersByMemberId(UUID memberId) {
        List<Voucher> vouchers = jdbcTemplate.query(
            "SELECT * FROM vouchers WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
            Collections.singletonMap("memberId", memberId.toString().getBytes()),
            voucherRowMapper);

        return vouchers;
    }

    @Override
    public Optional<Voucher> updateVoucher(Voucher voucher) {
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
    public Optional<Voucher> deleteVoucher(Voucher voucher) {
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
}
