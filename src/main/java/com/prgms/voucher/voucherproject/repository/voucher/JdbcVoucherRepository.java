package com.prgms.voucher.voucherproject.repository.voucher;

import com.prgms.voucher.voucherproject.domain.voucher.FixedAmountVoucher;
import com.prgms.voucher.voucherproject.domain.voucher.PercentDiscountVoucher;
import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import com.prgms.voucher.voucherproject.domain.voucher.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgms.voucher.voucherproject.util.JdbcUtils.toUUID;


@Component
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    public static final int ONLY_ONE_DATA = 1;

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(?)",
                    voucherRowMapper, voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Voucher voucher) {
        int save = jdbcTemplate.update("INSERT INTO voucher(voucher_id, voucher_type, discount) VALUES (UUID_TO_BIN(?), ? ,?)",
                voucher.getId().toString().getBytes(), voucher.getVoucherType().toString(), voucher.getDiscount());

        if (save != ONLY_ONE_DATA) {
            throw new IllegalArgumentException("바우처 저장에 실패하였습니다.");
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
        return vouchers;
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = ?", voucherId);
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        String voucherType = resultSet.getString("voucher_type");
        long discount = resultSet.getLong("discount");
        VoucherType createVoucherType = VoucherType.valueOf(voucherType);

        Voucher voucher = switch (createVoucherType) {
            case FIXED -> new FixedAmountVoucher(discount);
            case PERCENT -> new PercentDiscountVoucher(discount);
        };

        return voucher;
    };

}
