package com.prgrms.repository.voucher;

import com.prgrms.exception.NotUpdateException;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherCreator;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.DiscountCreator;
import com.prgrms.presentation.message.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Primary
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String COLUMN = "voucher_id, voucher_type, discount";
    private final DiscountCreator discountCreator;
    private final VoucherCreator voucherCreator;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate,
            DiscountCreator discountCreator, VoucherCreator voucherCreator) {
        this.jdbcTemplate = jdbcTemplate;
        this.discountCreator = discountCreator;
        this.voucherCreator = voucherCreator;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucher_id", voucher.getVoucherId());
            put("voucher_type", voucher.getVoucherType().name());
            put("discount", voucher.getVoucherDiscount().getDiscountAmount());
        }};
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
    public Vouchers getAllVoucher() {
        List<Voucher> vouchers = jdbcTemplate.query("select " + COLUMN + " from vouchers",
                getVoucherRowMapper());
        return new Vouchers(vouchers);
    }

    @Override
    public Optional<Voucher> findById(int voucher_id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select " + COLUMN + " from vouchers where voucher_id = :voucher_id",
                    Collections.singletonMap("voucher_id", voucher_id),
                    getVoucherRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            logger.debug(ErrorMessage.NO_RESULT_RETURN_EMPTY.getMessage(), e);
            return Optional.empty();
        }
    }

    public RowMapper<Voucher> getVoucherRowMapper() {
        return (resultSet, i) -> {
            int voucherId = resultSet.getInt("voucher_id");
            VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
            double discountValue = resultSet.getDouble("discount");
            Discount discount = discountCreator.createDiscount(voucherType, discountValue);
            return voucherCreator.createVoucher(voucherId, voucherType, discount);
        };
    }

}
