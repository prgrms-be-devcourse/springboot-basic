package me.programmers.springboot.basic.springbootbasic.voucher.repository;

import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
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
                "select * from fixed_voucher",
                fixVoucherRowMapper);
        List<Voucher> percentvouchers = jdbcTemplate.query(
                "select * from percent_voucher",
                percentVoucherRowMapper);

        fixvouchers.addAll(percentvouchers);

        return fixvouchers;
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
                "insert into percent_voucher(voucher_id, percent) " +
                        "values (uuid_to_bin(?), ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getPercent());
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
    }

    private void saveFixVoucher(FixedAmountVoucher voucher) {
        int update = jdbcTemplate.update(
                "insert into fixed_voucher(voucher_id, amount) " +
                        "values (uuid_to_bin(?), ?)",
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
                "update fixed_voucher set amount = ? " +
                        "where voucher_id = (uuid_to_bin(?))",
                voucher.getAmount(),
                voucher.getVoucherId());
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was inserted");
        }
    }

    private void updatePercentVoucher(PercentAmountVoucher voucher) {
        var update = jdbcTemplate.update(
                "update percent_voucher set percent = ? " +
                        "where voucher_id = (uuid_to_bin(?))",
                voucher.getPercent(),
                voucher.getVoucherId());
        if (update != 1) {
            throw new IllegalArgumentException("Nothing was inserted");
        }
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from fixed_voucher");
        jdbcTemplate.update("delete from percent_voucher");
    }
}
