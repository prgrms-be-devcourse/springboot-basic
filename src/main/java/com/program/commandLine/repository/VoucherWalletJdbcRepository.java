package com.program.commandLine.repository;

import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.model.voucher.VoucherFactory;
import com.program.commandLine.model.voucher.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.program.commandLine.util.JdbcUtil.toUUID;

@Component(value = "walletRepository")
@Profile("release")
public class VoucherWalletJdbcRepository implements VoucherWalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherFactory voucherFactory;

    public VoucherWalletJdbcRepository(DataSource dataSource, VoucherFactory voucherFactory) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.voucherFactory = voucherFactory;
    }

    private final RowMapper<Voucher> voucherRowMapper = new RowMapper<Voucher>() {
        @Override
        public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException {
            UUID voucherId = toUUID(rs.getBytes("voucher_id"));
            VoucherType type = VoucherType.getType(rs.getString("type"));
            int discount = rs.getInt("discount");
            boolean used = rs.getBoolean("used");

            return voucherFactory.createVoucher(type, voucherId, discount, used);
        }
    };

    @Override
    public void createWallet(UUID voucherId, UUID customerId) {
        String sql = "INSERT INTO voucher_wallet(voucher_id, customer_id, create_at)" +
                "  VALUES (UUID_TO_BIN(:voucherId),UUID_TO_BIN(:customerId),:createAt)";
        int update = jdbcTemplate.update(sql, Map.of(
                "voucherId", voucherId.toString().getBytes(),
                "customerId", customerId.toString().getBytes(),
                "createAt", Timestamp.valueOf(LocalDateTime.now())
        ));
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
    }


    @Override
    public void deleteWallet(UUID voucherId) {
        String sql = "delete from voucher_wallet WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        jdbcTemplate.update(sql, Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    @Override
    public List<Voucher> findNotIncludeWallet() {
        String sql = "select v.voucher_id,v.type,v.discount,v.used from vouchers v left outer join voucher_wallet vw " +
                "on v.voucher_id = vw.voucher_id where vw.voucher_id is null ";
        return jdbcTemplate.query(sql, voucherRowMapper);
    }


    @Override
    public UUID findCustomerWalletByVoucher(UUID voucherId) {
        String sql = "select customer_id from voucher_wallet where voucher_id = UUID_TO_BIN(:voucherId)";
        try {
            return jdbcTemplate.queryForObject(sql, Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    (rs, rowNum) -> toUUID(rs.getBytes("customer_id")));
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("! 할당되지않은 바우처 입니다.");
        }
    }
}
