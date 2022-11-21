package com.programmers.voucher.repository;

import com.programmers.voucher.repository.sql.VoucherRowMapper;
import com.programmers.voucher.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.message.ErrorMessage.DB_ERROR_LOG;
import static com.programmers.message.ErrorMessage.INSERT_ERROR;
import static com.programmers.voucher.repository.sql.VoucherSql.*;

@Repository
@Profile("dev")
public class DbVoucherRepository implements VoucherRepository {
    public static final String VOUCHER_ID = "voucherId";
    public static final String VOUCHER_TYPE = "voucherType";
    public static final String VOUCHER_VALUE = "voucherValue";
    public static final String CREATE_AT = "createAt";
    private final Logger log = LoggerFactory.getLogger(DbVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherRowMapper voucherRowMapper;

    @Autowired
    public DbVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.voucherRowMapper = new VoucherRowMapper();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_VOUCHER_BY_ID,
                    Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()), voucherRowMapper));
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            throw new RuntimeException(DB_ERROR_LOG.getMessage(), e);
        }
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return jdbcTemplate.query(FIND_ALL_VOUCHERS, voucherRowMapper);
    }

    @Override
    public Voucher registerVoucher(Voucher voucher) {
        Map<String, Object> voucherParamMap = new HashMap<>();
        voucherParamMap.put(VOUCHER_ID, voucher.getVoucherId().toString().getBytes());
        voucherParamMap.put(CREATE_AT, LocalDateTime.now());

        Map<String, Object> voucherRuleParamMap = new HashMap<>();
        voucherRuleParamMap.put(VOUCHER_ID, voucher.getVoucherId().toString().getBytes());
        voucherRuleParamMap.put(VOUCHER_TYPE, voucher.getClass().getSimpleName().replaceAll("Voucher", ""));
        voucherRuleParamMap.put(VOUCHER_VALUE, voucher.getValue());

        try {
            jdbcTemplate.update(INSERT_VOUCHER, voucherParamMap);
            jdbcTemplate.update(INSERT_VOUCHER_RULE, voucherRuleParamMap);
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage());
            throw new RuntimeException(INSERT_ERROR.getMessage());
        }

        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }
}
