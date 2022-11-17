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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.program.commandLine.util.JdbcUtil.*;

@Component(value = "voucherRepository")
@Profile("JDBC")
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherFactory voucherFactory;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, VoucherFactory voucherFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.voucherFactory = voucherFactory;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>(){{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("type",voucher.getVoucherType().getString());
            put("discount", voucher.getVoucherDiscount());
        }};
    }

    private final RowMapper<Voucher> voucherRowMapper = new RowMapper<Voucher>() {
        @Override
        public Voucher mapRow(ResultSet rs, int rowNum) throws SQLException { //resultSet, index
            var voucherId = toUUID(rs.getBytes("voucher_id"));
            var type = VoucherType.getType(rs.getString("type"));
            var discount = rs.getInt("discount");

            return voucherFactory.createVoucher(type,voucherId,discount);
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
        var update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id,type, discount)" +
                "  VALUES (UUID_TO_BIN(:voucherId),:type,:discount)",toParamMap(voucher));
        if(update != 1) throw new RuntimeException("Nothing was inserted!");
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
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
