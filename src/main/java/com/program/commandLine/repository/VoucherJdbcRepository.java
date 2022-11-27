package com.program.commandLine.repository;

import com.program.commandLine.model.VoucherWallet;
import com.program.commandLine.model.customer.Customer;
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
import java.util.*;

import static com.program.commandLine.util.JdbcUtil.toUUID;

@Component(value = "voucherRepository")
@Profile("release")
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherFactory voucherFactory;

    public VoucherJdbcRepository(DataSource dataSource, VoucherFactory voucherFactory) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.voucherFactory = voucherFactory;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("type", voucher.getVoucherType().getString());
            put("discount", voucher.getVoucherDiscount());
            put("used", voucher.getUsed());
        }};
    }

    private final RowMapper<Voucher> voucherRowMapper = new RowMapper<Voucher>() {
        @Override
        public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException { //resultSet, index
            UUID voucherId = toUUID(rs.getBytes("voucher_id"));
            VoucherType type = VoucherType.getType(rs.getString("type"));
            int discount = rs.getInt("discount");
            boolean used = rs.getBoolean("used");

            return voucherFactory.createVoucher(type, voucherId, discount, used);
        }
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()), voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Voucher usedUpdate(Voucher voucher) {
        String sql = "UPDATE vouchers SET  used= :used WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        int update = jdbcTemplate.update(sql, toParamMap(voucher));
        if (update != 1) throw new RuntimeException("Nothing was updated!");
        return voucher;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO vouchers(voucher_id, type, discount, used)" +
                "  VALUES (UUID_TO_BIN(:voucherId),:type,:discount ,:used )";
        int update = jdbcTemplate.update(sql, toParamMap(voucher));
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
        return voucher;
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
        int update = jdbcTemplate.update(sql, Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        if (update != 1) throw new RuntimeException("Nothing was inserted!");
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from vouchers";
        return jdbcTemplate.query(sql, voucherRowMapper);
    }


    @Override
    public void deleteAll() {
        String sql = "DELETE FROM vouchers";
        jdbcTemplate.getJdbcTemplate().update(sql);
    }

    @Override
    public int count() {
        String sql = "select count(*) from vouchers";
        return jdbcTemplate.getJdbcTemplate().queryForObject(sql, Integer.class);
    }

}
