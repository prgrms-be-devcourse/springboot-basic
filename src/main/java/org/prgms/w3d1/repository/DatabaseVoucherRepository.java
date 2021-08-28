package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DatabaseVoucherRepository implements VoucherRepository{

    private final JdbcTemplate jdbcTemplate;

    public DatabaseVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Voucher> rowMapper = (rs, rowNum) -> {
        var voucherId = toUUID(rs.getBytes("voucher_id"));
        var value = rs.getLong("value");
        var voucherType = rs.getString("voucher_type");
        return VoucherFactory.getVoucher(voucherId, value, voucherType);
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try{
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                    "select * from vouchers where voucher_id = UUID_TO_BIN(?)",
                    rowMapper, voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public void save(Voucher voucher) {
        var saveCount = jdbcTemplate.update("insert into vouchers(voucher_id, value, voucher_type) values (UUID_TO_BIN(?), ?, ?)",
            voucher.getVoucherId().toString().getBytes(),
            voucher.getVoucherValue(),
            voucher.getClass().getSimpleName());

        if(saveCount != 1){
            throw new RuntimeException("Nothing was saved");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", rowMapper);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers");
    }

    static UUID toUUID(byte[] customer_ids) {
        var byteBuffer = ByteBuffer.wrap(customer_ids);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
