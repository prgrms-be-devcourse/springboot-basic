package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.common.exception.NotFoundException;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {
    private static final String ERROR_MESSAGE_FOR_NULL = "잘못된 입력입니다.";
    private static final String INSERT_SQL = "INSERT INTO vouchers(type,amount) VALUES(:type,:amount)";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM vouchers WHERE id = :id";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM vouchers WHERE id = :id";
    private static final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private static final String SELECT_ALL_BY_TYPE_SQL = "SELECT * FROM vouchers WHERE type = :type";
    private static final String SELECT_ALL_BY_TYPE_AND_DATE_SQL = "SELECT * FROM vouchers WHERE type = :type AND DATE(created_at) BETWEEN :fromDate AND :toDate";
    private static final String SELECT_ALL_BY_DATE_SQL = "SELECT * FROM vouchers WHERE DATE(created_at) BETWEEN :fromDate AND :toDate";
    public static final String SELECT_LAST_INSERT_ID_SQL = "SELECT LAST_INSERT_ID()";

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
        SqlParameterSource parameterSource = getParameterSource(voucher);

        jdbcTemplate.update(INSERT_SQL, parameterSource);

        Long newId = jdbcTemplate.queryForObject(SELECT_LAST_INSERT_ID_SQL, new MapSqlParameterSource(), Long.class);


        List<Voucher> vouchers = jdbcTemplate.query(SELECT_BY_ID_SQL,
                                                    new MapSqlParameterSource().addValue("id", newId),
                                                    voucherRowMapper);
        if (vouchers.size() != 1) {
            throw new RuntimeException();
        }
        return vouchers.get(0);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(long id) {
        List<Voucher> foundProduct = jdbcTemplate.query(SELECT_BY_ID_SQL,
                                                        new MapSqlParameterSource().addValue("id", id),
                                                        voucherRowMapper);
        if (foundProduct.size() == 0 ) {
            return Optional.empty();
        }
        return Optional.of(foundProduct.get(0));
    }

    @Override
    public void deleteById(long id) {
        int updatedRows = jdbcTemplate.update(DELETE_BY_ID_SQL,
                                              new MapSqlParameterSource().addValue("id", id));

        if (updatedRows == 0) {
            throw new NotFoundException("id값이 "+id+" 인 상품을 찾을 수 없습니다.");
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        return jdbcTemplate.query(SELECT_ALL_BY_TYPE_SQL,
                                  new MapSqlParameterSource().addValue("type", type.toString()),
                                  voucherRowMapper);
    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherType type, LocalDate fromDate, LocalDate toDate) {
        return jdbcTemplate.query(SELECT_ALL_BY_TYPE_AND_DATE_SQL,
                                  new MapSqlParameterSource().addValue("type", type.toString())
                                                             .addValue("fromDate", fromDate)
                                                             .addValue("toDate", toDate),
                                  voucherRowMapper);
    }

    @Override
    public List<Voucher> findAllByDate(LocalDate fromDate, LocalDate toDate) {
        System.out.println(fromDate);
        System.out.println(toDate);
        return jdbcTemplate.query(SELECT_ALL_BY_DATE_SQL,
                                  new MapSqlParameterSource().addValue("fromDate", fromDate)
                                                             .addValue("toDate", toDate),
                                  voucherRowMapper);
    }

    private SqlParameterSource getParameterSource(Voucher voucher) {
        return new MapSqlParameterSource().addValue("id", voucher.getId())
                                          .addValue("type", voucher.getType().toString())
                                          .addValue("amount", voucher.getAmountValue())
                                          .addValue("createdAt", voucher.getCreatedAt())
                                          .addValue("updatedAt", voucher.getUpdatedAt());
    }
}
;