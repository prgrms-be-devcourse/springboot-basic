package com.pppp0722.vouchermanagement.repository.voucher;

import static com.pppp0722.vouchermanagement.entity.voucher.VoucherType.FIXED_AMOUNT;
import static com.pppp0722.vouchermanagement.entity.voucher.VoucherType.getVoucherType;
import static com.pppp0722.vouchermanagement.util.Util.toUUID;

import com.pppp0722.vouchermanagement.io.Console;
import com.pppp0722.vouchermanagement.entity.voucher.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.entity.voucher.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.entity.voucher.Voucher;
import com.pppp0722.vouchermanagement.entity.voucher.VoucherType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final Console console = Console.getInstance();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    public Voucher createVoucher(Voucher voucher) {
        int update = jdbcTemplate.update(
            "INSERT INTO vouchers(voucher_id, type, amount, member_id) VALUES(UNHEX(REPLACE(:voucherId, '-', '')), :type, :amount, UNHEX(REPLACE(:memberId, '-', '')))",
            toParamMap(voucher));

        if (update != 1) {
            logger.error("Nothing was created! (Voucher)");
            console.printError("Nothing was created (Voucher)");
        }

        return voucher;
    }

    @Override
    public List<Voucher> readVouchers() {
        List<Voucher> vouchers = jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);

        return vouchers;
    }

    @Override
    public Optional<Voucher> readVoucher(UUID voucherId) {
        try {
            return Optional.of(
                jdbcTemplate.queryForObject(
                    "SELECT * FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result! (Voucher)");
            console.printError("Got empty result! (Voucher)");

            return Optional.empty();
        }
    }

    @Override
    public Voucher updateVoucher(Voucher voucher) {
        int update = jdbcTemplate.update(
            "UPDATE vouchers SET type = :type, amount = :amount WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
            toParamMap(voucher));

        if (update != 1) {
            logger.error("Nothing was updated! (Voucher)");
            console.printError("Nothing was updated! (Voucher)");
        }

        return voucher;
    }

    @Override
    public Voucher deleteVoucher(Voucher voucher) {
        int update = jdbcTemplate.update(
            "DELETE FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
            toParamMap(voucher));

        if (update != 1) {
            logger.error("Nothing was deleted! (Voucher)");
            console.printError("Nothing was deleted! (Voucher)");
        }

        return voucher;
    }

    @Override
    public List<Voucher> readVouchersByMemberId(UUID memberId) {
        List<Voucher> vouchers = jdbcTemplate.query(
            "SELECT * FROM vouchers WHERE member_id = UNHEX(REPLACE(:memberId, '-', ''))",
            Collections.singletonMap("memberId", memberId.toString().getBytes()),
            voucherRowMapper);

        return vouchers;
    }
}
