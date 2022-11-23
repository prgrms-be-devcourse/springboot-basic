package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Profile("dev")
@Repository
public class VoucherJdbcRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean saveVoucher(Voucher voucher) {
        String sql = "insert into voucher (discount_amount, voucher_type) values (:discount_amount, :voucher_type)";
        jdbcTemplate.update(sql, toParamSource(voucher));
        return true;
    }

    @Override
    public Optional<Voucher> getVoucherById(long voucherId) {
        String sql = "select id, discount_amount, voucher_type from voucher where id = :id";
        Voucher voucher = jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", voucherId), new VoucherMapper());
        return Optional.ofNullable(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        String sql = "select * from voucher";
        List<Voucher> list = jdbcTemplate.query(sql, new VoucherMapper());
        return list;
    }

    private SqlParameterSource toParamSource(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue("id", voucher.getId())
                .addValue("voucher_type", voucher.getVoucherType().toString())
                .addValue("discount_amount", voucher.getDiscountAmount());
    }
}

class VoucherMapper implements RowMapper<Voucher> {
    public Voucher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Voucher voucher = new Voucher();
        voucher.setId(resultSet.getInt("id"));
        voucher.setDiscountAmount(resultSet.getDouble("discount_amount"));
        voucher.setVoucherType(VoucherType.valueOf(resultSet.getString("voucher_type")));
        return voucher;
    }
}
