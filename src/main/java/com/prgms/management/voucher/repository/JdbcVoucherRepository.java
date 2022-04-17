package com.prgms.management.voucher.repository;

import com.prgms.management.voucher.entity.FixedAmountVoucher;
import com.prgms.management.voucher.entity.PercentDiscountVoucher;
import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.entity.VoucherType;
import com.prgms.management.voucher.exception.VoucherNotSaveException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile({"default"})
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher findById(UUID voucherId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from voucher WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                    Collections.singletonMap("id", voucherId.toString()),
                    (rs, rowNum) -> mapToVoucher(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * from voucher", (rs, rowNum) -> mapToVoucher(rs));
    }

    @Override
    public Voucher save(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("id", voucher.getVoucherId().toString());
            put("name", "demo");
            put("type", voucher.getVoucherType());
            put("figure", voucher.getVoucherFigure());
        }};
        int result = jdbcTemplate.update("INSERT INTO voucher(id, name, type, figure) " +
                        "VALUES (UNHEX(REPLACE(:id, '-', '')), :name, :type, :figure)",
                paramMap);
        if (result == 1) {
            return voucher;
        }
        throw new VoucherNotSaveException();
    }

    private UUID toUUID(byte[] bytes) {
        var buffer = ByteBuffer.wrap(bytes);
        return new UUID(buffer.getLong(), buffer.getLong());
    }

    private Voucher mapToVoucher(ResultSet set) throws SQLException {
        UUID id = toUUID(set.getBytes("id"));
        String type = set.getString("type");
        String name = set.getString("name");
        int figure = set.getInt("figure");
        Timestamp createdAt = set.getTimestamp("created_at");

        if (type.equals(VoucherType.FIXED.toString())) {
            return new FixedAmountVoucher(id, name, figure, createdAt);
        } else {
            return new PercentDiscountVoucher(id, name, figure, createdAt);
        }
    }
}
