package org.prgrms.part1.engine.repository;

import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.VoucherType;
import org.prgrms.part1.exception.VoucherException;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("test")
public class JdbcVoucherRepository implements VoucherRepository{

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var value = resultSet.getLong(voucherType.getValueColumnName());
        return voucherType.createVoucher(voucherId, value, createdAt);
    };

    public JdbcVoucherRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers where voucher_id=UUID_TO_BIN(?);",
                    voucherRowMapper,
                    (Object) voucherId.toString().getBytes()));
        } catch(EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public List<Voucher> findByType(VoucherType voucherType) {
        return jdbcTemplate.query("select * from vouchers where voucher_type=?;",
                voucherRowMapper,
                voucherType.toString());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int insertCount = jdbcTemplate.update("insert into vouchers (voucher_id, voucher_type, " + voucher.getVoucherType().getValueColumnName() + ", created_at) values (UUID_TO_BIN(?), ?, ?, ?);",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getVoucherType().toString(),
                voucher.getValue(),
                voucher.getCreatedAt());
        if (insertCount != 1) {
            throw new VoucherException("Voucher cant be inserted!");
        }
        return voucher;
    }

    public Voucher update(Voucher voucher) {
        int updateCount = jdbcTemplate.update("update set "+ voucher.getVoucherType().getValueColumnName() + "= ? where voucher_id = UUID_TO_BIN(?);",
                voucher.getVoucherType().getValueColumnName(),
                voucher.getValue(),
                voucher.getVoucherId().toString().getBytes());
        if (updateCount < 1) {
            throw new VoucherException("Nothing was updated!");
        }
        return voucher;
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers");
    }

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
