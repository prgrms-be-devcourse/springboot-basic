package org.prgrms.kdt.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.prgrms.kdt.voucher.utils.VoucherSql.*;

@Profile("prod")
@Repository
public class JdbcVoucherManager implements VoucherManager {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherManager.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        long voucherId = resultSet.getLong("voucher_id");
        String type = resultSet.getString("type");
        long amount = resultSet.getLong("amount");

        return Voucher.from(voucherId, VoucherType.of(type), new VoucherAmount(String.valueOf(amount)));
    };

    public JdbcVoucherManager(NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("vouchers")
                .usingGeneratedKeyColumns("voucher_id");
    }

    private static Map<String, Object> toParamMap(Voucher voucher) {
        return Map.of(
                "voucherId", voucher.getId(),
                "type", voucher.getType().getType(),
                "amount", voucher.getAmount().getValue()
        );
    }

    @Override
    public Voucher save(Voucher voucher) {
        Map<String, ? extends Serializable> params = Map.of("type", voucher.getType().getType(), "amount", voucher.getAmount().getValue());
        long generatedId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Voucher.from(generatedId, voucher.getType(), voucher.getAmount());
    }

    @Override
    public List<Voucher> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL.getSql(), voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(long voucherId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(FIND_BY_ID.getSql(),
                    Collections.singletonMap("voucherId", voucherId),
                    voucherRowMapper
            ));
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update(DELETE_ALL.getSql(), Collections.emptyMap());
    }

    @Override
    public void update(Voucher voucher) {
        int update = namedParameterJdbcTemplate.update(UPDATE.getSql(),
                toParamMap(voucher)
        );
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was updated");
        }
    }

    @Override
    public void deleteById(long voucherId) {
        int update = namedParameterJdbcTemplate.update(DELETE_BY_ID.getSql(),
                Collections.singletonMap("voucherId", voucherId)
        );
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was deleted");
        }
    }
}
