package com.prgrms.repository.voucher;

import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherRegistry;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.dto.discount.DiscountCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static DiscountCreator discountCreator = new DiscountCreator();

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucher_id = resultSet.getInt("voucher_id");
        var voucher_type = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var discountValue = resultSet.getDouble("discount");
        var discount = discountCreator.createDiscount(discountValue, voucher_type);
        return voucher_type.createVoucher(voucher_id, discount);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucher_id", voucher.getVoucherId());
            put("voucher_type", voucher.getVoucherType().name());
            put("discount", voucher.getVoucherDiscount().getValue());
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, discount) VALUES (:voucher_id, :voucher_type, :discount)",
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public VoucherRegistry getAllVoucher() {
        List<Voucher> vouchers = jdbcTemplate.query("select * from vouchers", voucherRowMapper);
        return new VoucherRegistry(vouchers);
    }

    @Override
    public Optional<Voucher> findById(int voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = :voucher_id",
                    Collections.singletonMap("voucher_id", voucherId),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }
}
