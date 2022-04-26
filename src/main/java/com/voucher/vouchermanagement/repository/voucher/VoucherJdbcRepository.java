
package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.voucher.vouchermanagement.repository.JdbcUtils.toLocalDateTime;
import static com.voucher.vouchermanagement.repository.JdbcUtils.toUUID;

@Repository
@Profile("prod")
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        return this.jdbcTemplate.query(
                "SELECT * FROM vouchers",
                voucherRowMapper
        );
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(
                this.jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE id = UNHEX(REPLACE(:id, '-','')) ",
                        Collections.singletonMap("id", id.toString().getBytes()),
                        voucherRowMapper)
        );
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    @Override
    public void insert(Voucher voucher) {
        int update = this.jdbcTemplate.update("INSERT INTO vouchers VALUES(UNHEX(REPLACE(:id, '-', '')), :value, :type, :createdAt)", toParamMap(voucher));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted.");
        }
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        Long value = resultSet.getLong("value");
        VoucherType type = VoucherType.getVoucherTypeByName(resultSet.getString("type"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));

        return type.create(id, value, createdAt);
    };


    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", voucher.getVoucherId().toString().getBytes());
        paramMap.put("value", voucher.getValue());
        paramMap.put("type", voucher.getClass().getSimpleName());
        paramMap.put("createdAt", voucher.getCreatedAt().toString());

        return paramMap;
    }
}
