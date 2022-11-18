package com.program.commandLine.repository;

import com.program.commandLine.voucher.Voucher;
import com.program.commandLine.voucher.VoucherFactory;
import com.program.commandLine.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Profile("JDBC")
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherFactory voucherFactory;

    public VoucherJdbcRepository(DataSource dataSource, VoucherFactory voucherFactory) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.voucherFactory = voucherFactory;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>(){{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("type",voucher.getVoucherType().getString());
            put("discount", voucher.getVoucherDiscount());
            put("AssignedCustomerId", voucher.getAssignedCustomerId() != null ?
                    voucher.getAssignedCustomerId().toString().getBytes() : null);
            put("used", voucher.getUsed());
        }};
    }

    private final RowMapper<Voucher> voucherRowMapper = new RowMapper<Voucher>() {
        @Override
        public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException { //resultSet, index
            var voucherId = toUUID(rs.getBytes("voucher_id"));
            var type = VoucherType.getType(rs.getString("type"));
            var discount = rs.getInt("discount");
            var AssignedCustomerId = toUUID(rs.getBytes("assigned_customer_id"));
            var used = rs.getBoolean("used");

            return voucherFactory.createVoucher(type,voucherId,discount, AssignedCustomerId,used);
        }
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId",voucherId.toString().getBytes()), voucherRowMapper));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Voucher insertVoucher(Voucher voucher) {
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, type, discount, assigned_customer_id, used)" +
                "  VALUES (UUID_TO_BIN(:voucherId),:type,:discount,:AssignedCustomerId ,:used )",toParamMap(voucher));
        if(update != 1) throw new RuntimeException("Nothing was inserted!");
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public List<Voucher> findByAssignedCustomer(UUID customerId) {
        return jdbcTemplate.query("select * from vouchers WHERE assigned_customer_id = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("customerId",customerId.toString().getBytes()), voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update("DELETE FROM vouchers");
    }

    @Override
    public int count() {
        return jdbcTemplate.getJdbcTemplate().queryForObject("select count(*) from customers", Integer.class);
    }
}
