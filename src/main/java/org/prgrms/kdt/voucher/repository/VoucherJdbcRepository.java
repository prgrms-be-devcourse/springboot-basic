package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.customer.domain.vo.Email;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.vo.Type;
import org.prgrms.kdt.voucher.dto.VoucherDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


// TODO Customer와 Voucher를 cascade해야함!!
@Repository
public class VoucherJdbcRepository implements VoucherRepository {
    private static final int SUCCESS_NUMBER = 1;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        Email email = new Email(resultSet.getString("customer_email"));
        Type type = Type.valueOf(resultSet.getString("voucher_type"));
        long value = resultSet.getLong("voucher_value");
        LocalDateTime createdDate = resultSet.getTimestamp("voucher_created_date").toLocalDateTime();
        LocalDateTime modifiedDate = resultSet.getTimestamp("voucher_modified_date") != null ? resultSet.getTimestamp("voucher_modified_date").toLocalDateTime() : null;

        return new Voucher(createdDate, modifiedDate, voucherId, email, type, value);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    public Voucher insert(VoucherDto voucherDto) {
        Voucher voucher = voucherDto.of();
        int insert = jdbcTemplate.update("insert into voucher(voucher_id, customer_email, voucher_type, voucher_value, voucher_created_date, voucher_modified_date) values (UUID_TO_BIN(?), ?, ?, ?, ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getEmail().toString(),
                voucher.getType().toString(),
                voucher.getValue(),
                Timestamp.valueOf(voucher.getCreatedDate()),
                Timestamp.valueOf(voucher.getModifiedDate())
        );
        if (insert != SUCCESS_NUMBER) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from voucher", voucherRowMapper);
    }

    @Override
    public List<Voucher> findByEmail(Email email) {
        return jdbcTemplate.query("select * from voucher where customer_email = ?", voucherRowMapper, email.toString());
    }

    @Override
    public UUID deleteById(UUID id) {
        int result = jdbcTemplate.update("delete from voucher where voucher_id = UUID_TO_BIN(?)", id.toString().getBytes());
        if (result != SUCCESS_NUMBER) {
            throw new RuntimeException("Nothing was inserted");
        }
        return id;
    }

    @Override
    public List<Voucher> findByType(Type type) {
        return jdbcTemplate.query("select * from voucher where voucher_type = ?", voucherRowMapper, type.toString());
    }

    @Override
    public Voucher findById(UUID id) {
        return jdbcTemplate.queryForObject("select * from voucher where voucher_id = ?", voucherRowMapper, id.toString().getBytes());
    }

    @Override
    public List<Voucher> findByCreatedDate(LocalDateTime time) {
        return jdbcTemplate.query("select * from voucher where voucher_created_date = ?", voucherRowMapper, Timestamp.valueOf(time));
    }
}
