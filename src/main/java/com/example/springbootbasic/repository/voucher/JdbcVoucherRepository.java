package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.springbootbasic.repository.voucher.JdbcVoucherSql.*;

@Repository
@Profile("dev")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherType", voucher.getVoucherType().getVoucherType());
            put("voucherDiscountValue", voucher.getDiscountValue());
        }};
    }

    private static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        long voucherId = resultSet.getLong("voucher_id");
        long voucherDiscountValue = resultSet.getLong("voucher_discount_value");
        String voucherType = resultSet.getString("voucher_type");
        return VoucherFactory.of(voucherId, voucherDiscountValue, VoucherType.of(voucherType));
    };

    @Override
    public Voucher save(Voucher voucher) {
        try {
            jdbcTemplate.update(INPUT_VOUCHER_SQL.getSql(), toParamMap(voucher));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        try {
            return jdbcTemplate.query(SELECT_ALL_VOUCHERS_SQL.getSql(), voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Voucher> findAllVouchersByVoucherType(VoucherType type) {
        try {
            return jdbcTemplate.query(SELECT_ALL_VOUCHERS_BY_TYPE_SQL.getSql(),
                    Collections.singletonMap("voucherType", type.getVoucherType()), voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        try {
            jdbcTemplate.update(UPDATE_VOUCHER_TYPE_BY_VOUCHER_ID.getSql(), toParamMap(voucher));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update(DELETE_ALL_VOUCHERS.getSql(), Collections.emptyMap());
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }

    @Override
    public void deleteVouchersByVoucherType(VoucherType voucherType) {
        try {
            jdbcTemplate.update(DELETE_VOUCHERS_BY_TYPE.getSql(),
                    Collections.singletonMap("voucherType", voucherType.getVoucherType()));
        } catch (DataAccessException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }
}
