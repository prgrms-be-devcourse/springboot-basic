package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.exception.voucher.JdbcVoucherRepositoryExceptionMessage.VOUCHER_TYPE_NULL_EXCEPTION;
import static com.example.springbootbasic.repository.voucher.JdbcVoucherSql.*;

@Repository
public class JdbcVoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private SqlParameterSource toParamSource(Voucher voucher) {
        return new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId())
                .addValue("voucherType", voucher.getVoucherType().getVoucherType())
                .addValue("voucherDiscountValue", voucher.getDiscountValue());
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        long voucherId = resultSet.getLong("voucher_id");
        long voucherDiscountValue = resultSet.getLong("voucher_discount_value");
        String voucherType = resultSet.getString("voucher_type");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime startAt = resultSet.getTimestamp("start_at").toLocalDateTime();
        LocalDateTime endAt = resultSet.getTimestamp("end_at").toLocalDateTime();
        return VoucherFactory.of(
                voucherId,
                voucherDiscountValue,
                VoucherType.of(voucherType),
                createdAt,
                startAt,
                endAt);
    };

    public Voucher save(Voucher voucher) {
        GeneratedKeyHolder voucherIdHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(INPUT_VOUCHER_SQL.getSql(), toParamSource(voucher), voucherIdHolder);
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return VoucherFactory.of(voucherIdHolder.getKey().longValue(), voucher.getDiscountValue(), voucher.getVoucherType(), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(30));
    }

    public List<Voucher> findAllVouchers() {
        try {
            return jdbcTemplate.query(SELECT_ALL_VOUCHERS.getSql(), voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Voucher> findAllVouchersByVoucherType(VoucherType type) {
        try {
            validateVoucherTypeNull(type);
            return jdbcTemplate.query(SELECT_ALL_VOUCHERS_BY_TYPE.getSql(),
                    Collections.singletonMap("voucherType", type.getVoucherType()), voucherRowMapper);
        } catch (EmptyResultDataAccessException | IllegalArgumentException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private void validateVoucherTypeNull(VoucherType type) {
        if (type == null) {
            throw new IllegalArgumentException(VOUCHER_TYPE_NULL_EXCEPTION.getMessage());
        }
    }

    public Voucher update(Voucher voucher) {
        try {
            jdbcTemplate.update(UPDATE_VOUCHER_TYPE_BY_VOUCHER_ID.getSql(), toParamSource(voucher));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucher;
    }

    public Voucher findById(long voucherId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_VOUCHER_BY_ID.getSql(),
                    Collections.singletonMap("voucherId", voucherId), voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return VoucherFactory.of(0L, 1L, FIXED_AMOUNT,
                    LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now().plusDays(30));
        }
    }

    public List<Voucher> findVouchersBy(Long voucherId,
                                        VoucherType voucherType,
                                        LocalDateTime findStartAt,
                                        LocalDateTime findEndAt
    ) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        String sql = "SELECT * FROM VOUCHER WHERE 1=1 ";
        if (voucherId != null) {
            sql += "AND voucher_id = :voucherId ";
            paramSource.addValue("voucherId", voucherId);
        }
        if (voucherType != null) {
            sql += "AND voucher_type = :voucherType ";
            paramSource.addValue("voucherType", voucherType.getVoucherType());
        }

        if (findStartAt != null && findEndAt != null) {
            sql += "AND created_at BETWEEN :findStartAt AND :findEndAt";
            paramSource.addValue("findStartAt", findStartAt)
                    .addValue("findEndAt", findEndAt);
        }

        try {
            return jdbcTemplate.query(sql, paramSource, voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    public void deleteAllVouchers() {
        try {
            jdbcTemplate.update(DELETE_ALL_VOUCHERS.getSql(), Collections.emptyMap());
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }

    public void deleteVouchersByVoucherType(VoucherType voucherType) {
        try {
            jdbcTemplate.update(DELETE_VOUCHERS_BY_TYPE.getSql(),
                    Collections.singletonMap("voucherType", voucherType.getVoucherType()));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }

    public boolean deleteVoucherById(long voucherId) {
        try {
            int update = jdbcTemplate.update(DELETE_VOUCHER_BY_ID.getSql(), Collections.singletonMap("voucherId", voucherId));
            return update == 1;
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return false;
        }
    }
}
