package com.prgrms.management.voucher.repository;

import com.prgrms.management.customer.repository.JdbcCustomerRepository;
import com.prgrms.management.voucher.domain.Voucher;
import com.prgrms.management.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Profile({"jdbc","test"})
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUId(resultSet.getBytes("voucher_id"));
        var amount = resultSet.getLong("amount");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var voucherType = VoucherType.of(resultSet.getString("voucher_type"));
        var customerId = resultSet.getBytes("customer_id") != null
                ? toUUId(resultSet.getBytes("customer_id")) : null;
        return new Voucher(voucherId, amount, createdAt, voucherType, customerId);
    };

    static UUID toUUId(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    @Override
    public Voucher save(Voucher voucher) {
        int update = jdbcTemplate.update("insert into vouchers(voucher_id, amount, created_at, voucher_type) values (UUID_TO_BIN(?), ?, ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getAmount(),
                Timestamp.valueOf(voucher.getCreatedAt()),
                voucher.getVoucherType().equals(VoucherType.FIXED) ? "fixed" : "percent");
        if (update != 1) {
            throw new RuntimeException("Noting was inserted");
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from vouchers where voucher_id = UUID_TO_BIN(?)",
                    voucherRowMapper,
                    voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateVoucherByCustomerId(UUID voucherId,UUID customerId) {
        int update = jdbcTemplate.update("update vouchers set customer_id = UUID_TO_BIN(?) where voucher_id = UUID_TO_BIN(?)",
                customerId.toString().getBytes(),
                voucherId.toString().getBytes()
        );
        if (update != 1) {
            throw new RuntimeException("Noting was updated");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("delete from vouchers where voucher_id = UUID_TO_BIN(?)",
                voucherId.toString().getBytes());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers");
    }

    @Override
    public List<UUID> findCustomerIdByVoucherType(VoucherType voucherType) {
        List<Voucher> query = jdbcTemplate.query("select * from vouchers where voucher_type = ?",
                voucherRowMapper,
                voucherType.equals(VoucherType.FIXED) ? "fixed" : "percent");
        return query.stream().map(Voucher::getVoucherId).collect(Collectors.toList());
    }
}
