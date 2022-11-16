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

import static com.programmers.message.ErrorMessage.*;
import static com.programmers.voucher.repository.sql.VoucherSql.*;

@Repository
@Profile("dev")
public class DbVoucherRepository implements VoucherRepository{
    private final Logger log = LoggerFactory.getLogger(DbVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final VoucherRowMapper voucherRowMapper;

    @Autowired
    public DbVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.voucherRowMapper = new VoucherRowMapper();
    }
    
    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_VOUCHER_BY_ID,
                    Collections.singletonMap("voucherId", id.toString().getBytes()), voucherRowMapper));
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return jdbcTemplate.query(FIND_ALL_VOUCHERS, voucherRowMapper);
    }

    @Override
    public Voucher registerVoucher(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("createAt", LocalDateTime.now());

        Map<String, Object> voucherRuleMap = new HashMap<>();
        voucherRuleMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        voucherRuleMap.put("voucherType", voucher.getClass().getSimpleName().replaceAll("Voucher", ""));
        voucherRuleMap.put("voucherValue", voucher.getValue());

        jdbcTemplate.update(INSERT_VOUCHER, paramMap);
        int count = jdbcTemplate.update(INSERT_VOUCHER_RULE, voucherRuleMap);

        if (count != 1) {
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
