package org.prgrms.part1.engine.repository;

import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.VoucherType;
import org.prgrms.part1.exception.VoucherException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile({"test", "default"})
public class JdbcVoucherRepository implements VoucherRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var value = resultSet.getLong(voucherType.getValueColumnName());
        return voucherType.createVoucher(voucherId, value, createdAt);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<String, Object>() {{
           put("voucherId", voucher.getVoucherId().toString().getBytes());
           put("voucherType", voucher.getVoucherType().toString());
           put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
           put("value", voucher.getValue());
        }};
    }

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public int count() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper).size();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers where voucher_id=UNHEX(REPLACE(:voucherId, '-', ''));",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch(EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return jdbcTemplate.query("select * from vouchers where voucher_type=:voucherType;",
                Collections.singletonMap("voucherType", voucherType.toString()),
                voucherRowMapper);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var paramMap = toParamMap(voucher);
        int insertCount = jdbcTemplate.update("insert into vouchers (voucher_id, voucher_type, " + voucher.getVoucherType().getValueColumnName() + ", created_at) values (UNHEX(REPLACE(:voucherId, '-', '')), :voucherType, :value, :createdAt);",paramMap);
        if (insertCount != 1) {
            throw new VoucherException("Voucher cant be inserted!");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        var paramMap = toParamMap(voucher);
        int updateCount = jdbcTemplate.update("update vouchers set "+ voucher.getVoucherType().getValueColumnName() + "= :value where voucher_id = UNHEX(REPLACE(:voucherId, '-', ''));",paramMap);
        if (updateCount < 1) {
            throw new VoucherException("Nothing was updated!");
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers", Collections.emptyMap());
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
