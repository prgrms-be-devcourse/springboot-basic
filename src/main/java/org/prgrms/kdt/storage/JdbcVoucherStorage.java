package org.prgrms.kdt.storage;

import org.prgrms.kdt.exceptions.CustomerException;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("prod")
@Repository
public class JdbcVoucherStorage implements VoucherStorage{

    private static final String FIXED_VOUCHER_TYPE = "fixed";

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherStorage.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        String voucherId = resultSet.getString("voucher_id");
        String voucherType = resultSet.getString("type");
        int amount = resultSet.getInt("amount");
        String customerId = resultSet.getString("customer_id");

        if(voucherType.equals(FIXED_VOUCHER_TYPE)){
            return new FixedAmountVoucher(voucherId, amount, customerId);
        }
        return new PercentDiscountVoucher(voucherId, amount, customerId);
    };

    @Override
    public void save(Voucher voucher) {
        int update;
        if(voucher.getOwnerId().isEmpty()){
            update = jdbcTemplate.update("INSERT INTO voucher(voucher_id, type, amount) VALUES (?, ?, ?)",
                    voucher.getVoucherId(),
                    voucher.getVoucherType(),
                    voucher.getAmount()
            );
            validateUpdate(update);
            return;
        }
        update = jdbcTemplate.update("INSERT INTO voucher(voucher_id, type, amount, customer_id) VALUES (?, ?, ?, ?)",
                voucher.getVoucherId(),
                voucher.getVoucherType(),
                voucher.getAmount(),
                voucher.getOwnerId().get());
        validateUpdate(update);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from voucher", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(String voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("select * from voucher WHERE voucher_id = ?", voucherRowMapper, voucherId));
        } catch (EmptyResultDataAccessException e){
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    public void deleteById(String voucherId){
        jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = ?", voucherId);
    }

    private void validateUpdate(int result){
        if(result != 1) {
            throw new CustomerException("고객 저장에 실패했습니다.");
        }
    }
}
