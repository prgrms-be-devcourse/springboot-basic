/*
package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.service.CreateVoucherDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("customer_id"));
        VoucherType voucherType = VoucherType.getVoucherTypeByName(resultSet.getString("voucher_type"));
        long voucherValue = resultSet.getLong("voucher_value");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return voucherType.create(new CreateVoucherDto(voucherId, voucherValue, createdAt));
    };

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
    }

    @Override
    public void insert(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, voucher_type, voucher_value, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getClass().getSimpleName(),
                voucher.getValue(),
                voucher.getCreatedAt()
        );

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
*/
