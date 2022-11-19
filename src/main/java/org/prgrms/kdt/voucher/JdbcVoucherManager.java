package org.prgrms.kdt.voucher;

import org.prgrms.kdt.voucher.utils.VoucherSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.prgrms.kdt.voucher.utils.VoucherSql.*;

@Profile("prod")
@Repository
public class JdbcVoucherManager implements VoucherManager {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherManager.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcVoucherManager(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("vouchers")
                .usingGeneratedKeyColumns("voucher_id");
    }

    @Override
    public Voucher save(Voucher voucher) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", voucher.getType().getType());
        params.put("amount", voucher.getAmount().getValue());
        long generatedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Voucher.from(generatedId, voucher.getType(), voucher.getAmount());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL.getSql(), voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_ID.getSql(),
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
        jdbcTemplate.update(DELETE_ALL.getSql());
    }

    @Override
    public void update(Voucher voucher) {
        int update = jdbcTemplate.update(UPDATE.getSql(),
                voucher.getType().getType(),
                voucher.getAmount().getValue(),
                voucher.getId()
        );
        if (update != 1) {
            throw new RuntimeException("Notion was updated");
        }
    }

    @Override
    public void deleteById(long voucherId) {
        jdbcTemplate.update(DELETE_BY_ID.getSql(),
                voucherId
        );
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        long voucherId = resultSet.getLong("voucher_id");
        String type = resultSet.getString("type");
        long amount = resultSet.getLong("amount");

        return Voucher.from(voucherId, VoucherType.of(type), new VoucherAmount(String.valueOf(amount)));
    };
}
