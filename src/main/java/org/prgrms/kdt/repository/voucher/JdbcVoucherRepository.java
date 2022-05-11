package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.util.UUIDUtils.toUUID;

@Primary
@Qualifier("jdbc")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int insert = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, value, voucher_type) VALUES (UUID_TO_BIN(:voucherId), :value, :voucherType)",
            toParamMap(voucher));
        if (insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        int update = jdbcTemplate.update("UPDATE vouchers SET value = :value, voucher_type = :voucherType WHERE voucher_id = UUID_TO_BIN(:voucherId)",
            toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
            Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM vouchers");
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long value = resultSet.getLong("value");
        String typeName = resultSet.getString("voucher_type");
        VoucherType voucherType = VoucherType.valueOf(typeName);

        return voucherType.createVoucher(voucherId, value);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("value", voucher.getVoucherValue());
        VoucherType voucherType = VoucherType.getVoucherType(voucher.getClass());
        paramMap.put("voucherType", voucherType.toString());

        return paramMap;
    }
}
