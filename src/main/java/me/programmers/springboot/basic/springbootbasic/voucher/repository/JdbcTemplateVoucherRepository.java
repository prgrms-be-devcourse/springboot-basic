package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class JdbcTemplateVoucherRepository implements VoucherRepository {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateVoucherRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Voucher> fixVoucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        int amount = Integer.parseInt(resultSet.getString("amount"));
        return new FixedAmountVoucher(voucherId, amount);
    };

    private static RowMapper<Voucher> percentVoucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long percent = Integer.parseInt(resultSet.getString("percent"));
        return new PercentAmountVoucher(voucherId, percent);
    };

    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> fixvouchers = jdbcTemplate.query(
                "SELECT * FROM fixed_voucher",
                fixVoucherRowMapper);
        List<Voucher> percentvouchers = jdbcTemplate.query(
                "SELECT * FROM percent_voucher",
                percentVoucherRowMapper);

        fixvouchers.addAll(percentvouchers);

        return fixvouchers;
    }

    public List<Voucher> findAllFixVouchers() {
        List<Voucher> fixvouchers = jdbcTemplate.query(
                "SELECT * FROM fixed_voucher",
                fixVoucherRowMapper);

        return fixvouchers;
    }


    public List<Voucher> findAllPercentVouchers() {
        List<Voucher> percentvouchers = jdbcTemplate.query(
                "SELECT * FROM percent_voucher",
                percentVoucherRowMapper);

        return percentvouchers;
    }

    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Optional<Voucher> fixVoucher = Optional.of(jdbcTemplate.queryForObject(
                    "SELECT * FROM fixed_voucher WHERE voucher_id = (uuid_to_bin(?))",
                    fixVoucherRowMapper,
                    voucherId.toString().getBytes()));
            return fixVoucher;
        } catch (EmptyResultDataAccessException e) {}

        try {
            Optional<Voucher> percentVoucher = Optional.of(jdbcTemplate.queryForObject(
                    "SELECT * FROM percent_voucher WHERE voucher_id = (uuid_to_bin(?))",
                    percentVoucherRowMapper,
                    voucherId.toString().getBytes()));
            return percentVoucher;
        } catch (EmptyResultDataAccessException e) {
        }

        return Optional.empty();
    }

    @Override
    public Voucher save(Voucher voucher) {
        if (voucher instanceof FixedAmountVoucher)
            saveFixVoucher((FixedAmountVoucher) voucher);
        else if (voucher instanceof PercentAmountVoucher)
            savePercentVoucher((PercentAmountVoucher) voucher);

        return voucher;
    }

    private void savePercentVoucher(PercentAmountVoucher voucher) {
        int update = jdbcTemplate.update(
                "INSERT INTO percent_voucher(voucher_id, percent) " +
                        "VALUES (uuid_to_bin(?), ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getPercent());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    private void saveFixVoucher(FixedAmountVoucher voucher) {
        int update = jdbcTemplate.update(
                "INSERT INTO fixed_voucher(voucher_id, amount) " +
                        "VALUES (uuid_to_bin(?), ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getAmount());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    public Voucher update(Voucher voucher) {
        if (voucher instanceof FixedAmountVoucher)
            updateFixVoucher((FixedAmountVoucher) voucher);
        else if (voucher instanceof PercentAmountVoucher)
            updatePercentVoucher((PercentAmountVoucher) voucher);

        return voucher;
    }

    private void updateFixVoucher(FixedAmountVoucher voucher) {
        var update = jdbcTemplate.update(
                "UPDATE fixed_voucher SET amount = ? " +
                        "WHERE voucher_id = UUID_TO_BIN(?)",
                voucher.getAmount(),
                voucher.getVoucherId().toString().getBytes());
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was inserted");
        }
    }

    private void updatePercentVoucher(PercentAmountVoucher voucher) {
        var update = jdbcTemplate.update(
                "UPDATE percent_voucher SET percent = ? " +
                        "WHERE voucher_id = UUID_TO_BIN(?)",
                voucher.getPercent(),
                voucher.getVoucherId().toString().getBytes());
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was inserted");
        }
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM fixed_voucher");
        jdbcTemplate.update("DELETE FROM percent_voucher");
    }
}
