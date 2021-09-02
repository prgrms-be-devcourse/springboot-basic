package com.prgrms.devkdtorder.voucher.repository;

import com.prgrms.devkdtorder.util.Utils;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = Utils.toUUID(resultSet.getBytes("voucher_id"));
        var name = resultSet.getString("name");
        var value = resultSet.getLong("value");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime().truncatedTo(ChronoUnit.MILLIS);
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        return voucherType.createVoucher(voucherId, value, name, createdAt);
    };


    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("name", voucher.getName());
            put("value", voucher.getValue());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("voucherType", voucher.getClass().getSimpleName().replace("Voucher","").toUpperCase());
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO vouchers(voucher_id, value, name, created_at, voucher_type) VALUES (UNHEX(REPLACE(:voucherId, '-', '')), :value, :name, :createdAt, :voucherType)";
        Map<String, Object> paramMap = toParamMap(voucher);
        int insert = jdbcTemplate.update(sql, paramMap);
        if (insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }


    @Override
    public Voucher update(Voucher voucher) {
        String sql = "UPDATE vouchers SET value = :value, name = :name, voucher_type = :voucherType WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
        int update = jdbcTemplate.update(sql, toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            String sql = "SELECT * FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucher_id, '-', ''))";
            Voucher customer = jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("voucher_id", voucherId.toString().getBytes()),
                    voucherRowMapper);
            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM vouchers");
    }


}
