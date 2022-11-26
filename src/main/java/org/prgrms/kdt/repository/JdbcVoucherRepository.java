package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
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

@Profile("prod")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(MapVoucherRepository.class);

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> saveVoucher(Voucher voucher) {
        GeneratedKeyHolder voucherIdHolder = new GeneratedKeyHolder();
        try {
            String sql = "insert into voucher (discount_amount, voucher_type) values (:discount_amount, :voucher_type)";
            jdbcTemplate.update(sql, toParamSource(voucher), voucherIdHolder);
        } catch(DataAccessException e){
            logger.error("[Repository] <saveVoucher> : ", e);
        }
        Voucher savedVoucher = new Voucher(voucherIdHolder.getKey().longValue(), voucher.getVoucherType(), voucher.getDiscountAmount());
        logger.info("[Repository] save {}", savedVoucher);
        return Optional.ofNullable(savedVoucher);
    }

    @Override
    public Optional<Voucher> getVoucherById(long voucherId) {
        Voucher returnedVoucher = null;
        try {
            String sql = "select id, discount_amount, voucher_type from voucher where id = :id";
            returnedVoucher = jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", voucherId), new VoucherMapper());
        } catch(DataAccessException e){
            logger.error("[Repository] <getVoucherById> : ", e);
        }
        logger.info("[Repository] get voucher {}", returnedVoucher);
        return Optional.ofNullable(returnedVoucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        List<Voucher> returnedVouchers = List.of();
        try {
            String sql = "select * from voucher";
            returnedVouchers = jdbcTemplate.query(sql, new VoucherMapper());
        } catch(DataAccessException e){
            logger.error("[Repository] <getAllVouchers> : ", e);
        }
        logger.info("[Repository] get all voucher {}", returnedVouchers);
        return returnedVouchers;
    }

    private SqlParameterSource toParamSource(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue("id", voucher.getId())
                .addValue("voucher_type", voucher.getVoucherType().toString())
                .addValue("discount_amount", voucher.getDiscountAmount());
    }
    class VoucherMapper implements RowMapper<Voucher> {
        public Voucher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Voucher voucher = new Voucher();
            voucher.setId(resultSet.getLong("id"));
            voucher.setDiscountAmount(resultSet.getDouble("discount_amount"));
            voucher.setVoucherType(VoucherType.valueOf(resultSet.getString("voucher_type")));
            return voucher;
        }
    }
}
