package com.prgrms.repository.voucher;

import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.dto.discount.Discount;
import com.prgrms.model.voucher.dto.discount.DiscountCreator;
import com.prgrms.view.message.ErrorMessage;
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
    private static DiscountCreator discountCreator = new DiscountCreator();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        int voucherId = resultSet.getInt("voucher_id");
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        double discountValue = resultSet.getDouble("discount");
        Discount discount = discountCreator.createDiscount(discountValue, voucherType);
        return voucherType.createVoucher(voucherId, discount);
    };

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucher_id", voucher.getVoucherId());
            put("voucher_type", voucher.getVoucherType().name());
            put("discount", voucher.getVoucherDiscount().getValue());
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, discount) VALUES (:voucher_id, :voucher_type, :discount)",
                toParamMap(voucher));

        if (update != 1) {
            throw new RuntimeException(ErrorMessage.NOT_UPDATE.getMessage());
        }

        return voucher;
    }

    @Override
    public Vouchers getAllVoucher() {
        List<Voucher> vouchers = jdbcTemplate.query("select * from vouchers", voucherRowMapper);
        return new Vouchers( vouchers);
    }

    @Override
    public Optional<Voucher> findById(int voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = :voucher_id",
                    Collections.singletonMap("voucher_id", voucherId),
                    voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("데이터를 찾을 수 없습니다.", e);
            return Optional.empty();
        }
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }
}
