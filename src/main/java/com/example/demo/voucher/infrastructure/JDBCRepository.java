package com.example.demo.voucher.infrastructure;

import com.example.demo.voucher.application.VoucherType;
import com.example.demo.voucher.domain.Voucher;
import com.example.demo.voucher.domain.repository.VoucherRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.demo.common.utils.UUIDParser.toBytes;
import static com.example.demo.common.utils.UUIDParser.toUUID;

@Repository
@Primary
public class JDBCRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> rowMapper = (rs, rowNum) -> {
        UUID id = toUUID(rs.getBytes("voucher_id"));
        long value = rs.getLong("voucher_value");
        String type = rs.getString("voucher_type");

        return VoucherType.fromCounter(type)
                .orElseThrow(() -> new IllegalArgumentException("Unknown voucher type: " + type))
                .createVoucher(id, value);
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "SELECT * FROM vouchers WHERE voucher_id = ?";

        return jdbcTemplate.query(sql, rowMapper, toBytes(voucherId)).stream().findAny();
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM vouchers";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void insert(Voucher voucher) {
        String sql = "INSERT INTO vouchers (voucher_id, name, voucher_value, voucher_type) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, toBytes(voucher.getVoucherId()), voucher.getName(), voucher.getValue(), voucher.getType().getCounter());
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM vouchers";
        jdbcTemplate.update(sql);
    }
}
