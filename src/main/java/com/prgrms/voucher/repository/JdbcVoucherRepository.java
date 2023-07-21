package com.prgrms.voucher.repository;

import com.prgrms.exception.NotUpdateException;
import com.prgrms.voucher.model.Voucher;
import com.prgrms.voucher.model.VoucherCreator;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.model.Vouchers;
import com.prgrms.voucher.model.discount.Discount;
import com.prgrms.voucher.model.discount.DiscountCreator;
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
    public Vouchers getAllVoucher() {
        List<Voucher> vouchers = jdbcTemplate.query(
                "select voucher_id, voucher_type, discount from vouchers",
                getVoucherRowMapper());
        return new Vouchers(vouchers);
    }

    @Override
    public Optional<Voucher> findById(int voucher_id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select voucher_id, voucher_type, discount from vouchers where voucher_id = :voucher_id",
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
