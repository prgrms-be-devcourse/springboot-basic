package com.prgrms.springbasic.domain.voucher.repository;

import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String INSERT_QUERY = "INSERT INTO vouchers(voucher_id, discount_type, discount_value) VALUES(UUID_TO_BIN(?), ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM vouchers";
    private static final String FIND_BY_ID = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String UPDATE_QUERY = "UPDATE vouchers SET discount_value = ? WHERE voucher_id = UUID_TO_BIN(?)";
    private static final String DELETE_ALL = "DELETE from vouchers";
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = UUIDUtils.toUUID(resultSet.getBytes("voucher_id"));
        String discountType = resultSet.getString("discount_type");
        long discountValue = resultSet.getLong("discount_value");
        return Voucher.createVoucher(voucherId, discountType, discountValue);
    };

    @Override
    public Voucher saveVoucher(Voucher voucher) {
        jdbcTemplate.update(INSERT_QUERY,
                voucher.getVoucherId().toString().getBytes(),
                voucher.getDiscountType().toString(),
                voucher.getDiscountValue());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL, voucherRowMapper);
    }

    @Override
    public void updateVoucher(Voucher voucher) {
        jdbcTemplate.update(UPDATE_QUERY,
                voucher.getDiscountValue(),
                voucher.getVoucherId().toString().getBytes());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    @Override
    public Optional<Voucher> findVoucherById(UUID voucher_id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID,
                    voucherRowMapper, voucher_id.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }
}
