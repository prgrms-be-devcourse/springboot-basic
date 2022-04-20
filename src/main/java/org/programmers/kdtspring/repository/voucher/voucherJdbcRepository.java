package org.programmers.kdtspring.repository.voucher;

import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


import static org.programmers.kdtspring.repository.voucher.SQLString.*;

@Repository
@Primary
public class VoucherJdbcRepository implements VoucherRepository {

    private static final int NOT_BELONGED_TO = 0;

    private static final Logger log = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> mapToVoucher = (resultSet, i) -> {
        Long voucherId = resultSet.getLong("voucher_Id");
        Long customerId = resultSet.getLong("customer_id") != NOT_BELONGED_TO ?
                resultSet.getLong("customer_id") : null;
        String voucherType = resultSet.getString("voucher_type");
        if (voucherType.equals(String.valueOf(VoucherType.FixedAmountVoucher))) {
            int amount = resultSet.getInt("amount");
            return new FixedAmountVoucher(voucherId, customerId, amount, voucherType);
        } else {
            int percent = resultSet.getInt("percent");
            return new PercentDiscountVoucher(voucherId, percent, voucherType);
        }
    };

    @Override
    public void save(Voucher voucher) {
        log.info("[voucherJdbcRepository] save() called");

        if (voucher instanceof FixedAmountVoucher) {
            int insert = jdbcTemplate.update(INSERT_VOUCHER,
                    voucher.getVoucherId(),
                    voucher.getCustomerId(),
                    String.valueOf(VoucherType.FixedAmountVoucher),
                    ((FixedAmountVoucher) voucher).getDiscount(),
                    null);
            if (insert != 1) {
                throw new RuntimeException("Nothing was inserted");
            }
            return;
        }

        if (voucher instanceof PercentDiscountVoucher) {
            int insert = jdbcTemplate.update(
                    INSERT_VOUCHER,
                    voucher.getVoucherId(),
                    VoucherType.PercentDiscountVoucher.toString(),
                    null,
                    ((PercentDiscountVoucher) voucher).getDiscount());
            if (insert != 1) {
                throw new RuntimeException("Nothing was inserted");
            }
        }
    }

    @Override
    public Optional<Voucher> findById(Long voucherId) {
        log.info("[voucherJdbcRepository] findById() called");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID,
                    mapToVoucher,
                    voucherId));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        return jdbcTemplate.query(SELECT_BY_CUSTOMER,
                mapToVoucher,
                customer.getCustomerId());
    }

    @Override
    public List<Voucher> findAll() {
        log.info("[voucherJdbcRepository] findAll() called");
        return jdbcTemplate.query(SELECT_ALL, mapToVoucher);
    }

    @Override
    public Voucher updateCustomerId(Voucher voucher) {
        log.info("[VoucherService] updateCustomerId called()");

        int update = jdbcTemplate.update(
                UPDATE_BY_ID_SQL,
                voucher.getCustomerId(),
                voucher.getVoucherId());
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public void deleteOne(Voucher voucher) {
        log.info("[voucherJdbcRepository] deleteOne() called");

        int delete = jdbcTemplate.update(DELETE_VOUCHER,
                voucher.getVoucherId().toString());
        if (delete != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public void deleteAll() {
        log.info("[voucherJdbcRepository] deleteAll() called");

        jdbcTemplate.update(DELETE_ALL);
    }
}
