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

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programmers.kdtspring.repository.voucher.SQLString.*;

@Repository
@Primary
public class voucherJdbcRepository implements VoucherRepository {

    private static final Logger log = LoggerFactory.getLogger(voucherJdbcRepository.class);
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public voucherJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.get());
    }

    private final RowMapper<Voucher> mapToVoucher = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_Id"));
        UUID customerId = resultSet.getBytes("customer_id") != null ?
                toUUID(resultSet.getBytes("customer_id")) : null;
        String voucherType = resultSet.getString("voucher_type");
        if (voucherType.equals(String.valueOf(VoucherType.FixedAmountVoucher))) {
            int amount = resultSet.getInt("amount");
            return new FixedAmountVoucher(voucherId, customerId, amount);
        } else {
            int percent = resultSet.getInt("percent");
            return new PercentDiscountVoucher(voucherId, customerId, percent);
        }
    };


    @Override
    public void save(Voucher voucher) {
        log.info("[voucherJdbcRepository] save() called");

        if (voucher instanceof FixedAmountVoucher) {
            int insert = jdbcTemplate.update(INSERT_VOUCHER, voucher.getVoucherId().toString().getBytes(),
                    VoucherType.FixedAmountVoucher.toString(),
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
                    voucher.getVoucherId().toString().getBytes(),
                    VoucherType.PercentDiscountVoucher.toString(),
                    null,
                    ((PercentDiscountVoucher) voucher).getDiscount());
            if (insert != 1) {
                throw new RuntimeException("Nothing was inserted");
            }
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        log.info("[voucherJdbcRepository] findById() called");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID,
                    mapToVoucher,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            log.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        return jdbcTemplate.query(SELECT_BY_CUSTOMER,
                mapToVoucher,
                customer.getCustomerId().toString().getBytes());
    }

    @Override
    public List<Voucher> findAll() {
        log.info("[voucherJdbcRepository] findAll() called");
        return jdbcTemplate.query(SELECT_ALL, mapToVoucher);
    }

    @Override
    public void deleteOne(Voucher voucher) {
        log.info("[voucherJdbcRepository] deleteOne() called");

        int delete = jdbcTemplate.update(DELETE_VOUCHER,
                voucher.getVoucherId().toString().getBytes());
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
