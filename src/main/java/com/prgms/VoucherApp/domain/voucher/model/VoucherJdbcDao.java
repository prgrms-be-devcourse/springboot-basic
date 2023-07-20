package com.prgms.VoucherApp.domain.voucher.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class VoucherJdbcDao implements VoucherDao {

    private final Logger logger = LoggerFactory.getLogger(VoucherJdbcDao.class);
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public VoucherJdbcDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "INSERT INTO voucher VALUES (:id, :amount, :type)";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", voucher.getVoucherId().toString())
                .addValue("amount", voucher.getAmount())
                .addValue("type", voucher.getVoucherType().getVoucherTypeName());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count != 1) {
            logger.warn("할인권이이 생성되지 않은 예외가 발생 입력 값 {}", voucher);
            throw new IllegalArgumentException("입력 값의 문제로 할인권이 생성되지 못했습니다.");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM voucher";

        List<Voucher> vouchers = namedParameterJdbcTemplate.query(sql, voucherRowMapper());
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "SELECT * FROM voucher where id = :id";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", voucherId.toString());

        try {
            Voucher voucher = namedParameterJdbcTemplate.queryForObject(sql, paramMap, voucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException exception) {
            return Optional.empty();
        } catch (IncorrectResultSizeDataAccessException exception) {
            logger.warn("쿼리 수행 결과가 2개 이상입니다.", exception);
            throw new RuntimeException("단 건 조회 시도 결과 쿼리 결과가 2개 이상입니다.", exception);
        }
    }

    @Override
    public List<Voucher> findByVoucherType(VoucherType type) {
        String sql = "SELECT * FROM voucher where type = :type";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("type", type.getVoucherTypeName());

        List<Voucher> vouchers = namedParameterJdbcTemplate.query(sql, paramMap, voucherRowMapper());
        return vouchers;
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "UPDATE voucher SET amount = :amount, type = :type WHERE id = :id";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("amount", voucher.getAmount())
                .addValue("type", voucher.getVoucherType().getVoucherTypeName())
                .addValue("id", voucher.getVoucherId().toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            logger.warn("존재하지 않는 아이디가 입력되어 업데이트하지 못하는 예외가 발생 id = {}", voucher.getVoucherId());
            throw new IllegalArgumentException("존재하지 않는 id 를 입력 받았습니다.");
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "DELETE FROM voucher WHERE id = :id";

        MapSqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("id", voucherId.toString());

        int count = namedParameterJdbcTemplate.update(sql, paramMap);

        if (count == 0) {
            logger.warn("존재하지 않는 아이디가 입력되어 삭제하지 못하는 예외가 발생 id = {}", voucherId);
            throw new IllegalArgumentException("존재하지 않는 id 를 입력받았습니다.");
        }
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            BigDecimal amount = resultSet.getBigDecimal("amount");
            String type = resultSet.getString("type");
            VoucherType voucherType = VoucherType.findByVoucherTypeName(type);
            if (voucherType.isFixedVoucher()) {
                return new FixedAmountVoucher(UUID.fromString(id), amount);
            }
            return new PercentDiscountVoucher(UUID.fromString(id), amount);
        };
    }
}
