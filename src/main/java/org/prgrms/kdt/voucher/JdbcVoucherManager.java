package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("prod")
@Repository
public class JdbcVoucherManager implements VoucherManager {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherManager.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        long voucherId = resultSet.getLong("voucher_id");
        String type = resultSet.getString("type");
        long amount = resultSet.getLong("amount");

        return Voucher.from(voucherId, VoucherType.of(type), new VoucherAmount(String.valueOf(amount)));
    };

    @Override
    public void save(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(`type`, amount) VALUES (?, ?)",
                voucher.getType().getType(),
                voucher.getAmount().getValue()
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = ?",
                    voucherRowMapper,
                    id
            ));
        } catch (EmptyResultDataAccessException exception) {
            logger.error("Got Empty Result", exception);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers");
    }

    @Override
    public void update(Voucher voucher) {
        int update = jdbcTemplate.update("UPDATE vouchers SET type = ?, amount = ?",
                voucher.getType().getType(),
                voucher.getAmount().getValue()
        );
        if (update != 1) {
            throw new RuntimeException("Notion was updated");
        }
    }

    @Override
    public void deleteById(long voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id");
    }
}
