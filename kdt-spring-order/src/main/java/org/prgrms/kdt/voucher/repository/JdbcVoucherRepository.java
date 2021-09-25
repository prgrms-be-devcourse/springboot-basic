package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdt.common.util.JdbcUtil.toLocalDateTime;
import static org.prgrms.kdt.common.util.JdbcUtil.toUUID;
import static org.prgrms.kdt.common.util.VoucherUtil.createVoucherByType;

@Repository
public class JdbcVoucherRepository implements VoucherRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("value", voucher.getValue());
            put("type", voucher.getType().toString());
            put("createdAt", voucher.getCreatedAt());
        }};
    }

    private RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId =  toUUID(resultSet.getBytes("voucher_id"));
        var value = resultSet.getLong("value");
        var type = VoucherType.convert(resultSet.getString("type"));
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        return createVoucherByType(voucherId, value, createdAt, type);
    };

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update(
                "insert into vouchers(voucher_id, value, type, created_at) values (UUID_TO_BIN(:voucherId), :value, :type, :createdAt)",
                toParamMap(voucher)
        );
        if (update != 1)
            throw new RuntimeException("Failed insert");
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public List<Voucher> findByParams(String from, String to, String type) {
        if("ALL".equals(type)){
            return jdbcTemplate.query(
                    "select * from vouchers where created_at between :from and :to ",
                    new HashMap<>() {{
                            put("from", from);
                            put("to", to);
                        }},
                    voucherRowMapper
            );
        }
        else{
            return jdbcTemplate.query(
                    "select * from vouchers where created_at between :from and :to and type = :type",
                    new HashMap<>() {{
                        put("from", from);
                        put("to", to);
                        put("type", type);
                    }},
                    voucherRowMapper
            );
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "select * from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(
                "delete from vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes())
        );
    }

}
