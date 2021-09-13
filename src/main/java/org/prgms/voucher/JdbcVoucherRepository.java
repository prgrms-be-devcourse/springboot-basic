package org.prgms.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgms.Utils.toUUID;


@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var amount = resultSet.getLong("amount");
        return switch (voucherType) {
            case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount, voucherType);
            case PERCENT_DISCOUNT -> new PercentDiscountVoucher(voucherId, amount, voucherType);
        };
    };


    public JdbcVoucherRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("select * from vouchers where voucher_id =  (UNHEX(REPLACE(?,'-','')))",
                            voucherRowMapper, voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, amount, created_at) VALUES (UNHEX(REPLACE(?,'-','')),?,?,?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getVoucherType().toString(),
                voucher.getAmount(),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType voucherType) {
        return jdbcTemplate.query("select * from vouchers where voucher_type = ?", voucherRowMapper, voucherType.toString());
    }

}
