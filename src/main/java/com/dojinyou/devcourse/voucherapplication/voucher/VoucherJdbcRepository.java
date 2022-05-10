package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {
    private static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    private static final String SELECT_ALL_SQL = "SELECT * FROM voucher";
    private static final String INSERT_SQL = "INSERT INTO voucher(type,amount) VALUES(:type,:amount)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Voucher> voucherRowMapper = (rs, i) -> VoucherMapper.resultSetToDomain(rs);

    public VoucherJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher create(Voucher voucher) {
        if (voucher == null) {
            throw new IllegalArgumentException(ERROR_MESSAGE_FOR_NULL);
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource parameterSource = getParameterSource(voucher);

        jdbcTemplate.update(INSERT_SQL, parameterSource, keyHolder);
        if (keyHolder.getKey() == null) {
            throw new RuntimeException("Not Insert Voucher");
        }

        return VoucherMapper.getDomain(keyHolder.getKey().longValue(),
                                       voucher.getVoucherType(),
                                       voucher.getVoucherAmount());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    private SqlParameterSource getParameterSource(Voucher voucher) {
        return new MapSqlParameterSource().addValue("type", voucher.getVoucherType().toString())
                                          .addValue("amount", voucher.getAmount());
    }
}
;