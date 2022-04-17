package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID save(Voucher voucher) {
        int update = jdbcTemplate.update(
                "INSERT INTO voucher(voucher_id, voucher_type, discount_value, customer_id, created_date, modified_date) " +
                        "VALUES (UNHEX(REPLACE(:voucherId, '-', '')), :voucherType, :discountValue, UNHEX(REPLACE(:customerId, '-', '')), :createdDate, :modifiedDate)",
                toParamMap(voucher));
        if(update != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher.getVoucherId();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Voucher voucher = jdbcTemplate.queryForObject("SELECT * FROM voucher WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                    Collections.singletonMap("voucherId", UuidUtils.UuidToByte(voucherId)), voucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Find By Voucher id got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT * FROM voucher WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                Collections.singletonMap("customerId", UuidUtils.UuidToByte(customerId)),
                voucherRowMapper());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM voucher", Collections.emptyMap(), voucherRowMapper());
    }

    @Override
    public int updateById(Voucher voucher) {
        int update = jdbcTemplate.update("UPDATE voucher " +
                        "SET voucher_type = :voucherType, discount_value = :discountValue, customer_id = UNHEX(REPLACE(:customerId, '-', '')), created_date = :createdDate, modified_date = :modifiedDate",
                toParamMap(voucher));
        return update;
    }

    @Override
    public void deleteById(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM voucher WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                Collections.singletonMap("voucherId", UuidUtils.UuidToByte(voucherId)));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
    }

    @Override
    public void deleteByCustomerId(UUID customerId) {
        jdbcTemplate.update("DELETE FROM voucher WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                Collections.singletonMap("customerId", UuidUtils.UuidToByte(customerId)));
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("voucherId", UuidUtils.UuidToByte(voucher.getVoucherId()));
            put("voucherType", VoucherType.getValue(voucher.getVoucherType()));
            put("discountValue", voucher.getDiscountValue());
            put("customerId", voucher.getCustomerId() == null ? null : UuidUtils.UuidToByte(voucher.getCustomerId()));
            put("createdDate", voucher.getCreatedDate());
            put("modifiedDate", voucher.getModifiedDate());
        }};
        return paramMap;
    }

    private RowMapper<Voucher> voucherRowMapper() {
        RowMapper<Voucher> rowMapper = (rs, rowNum) -> {
            UUID voucherId = UuidUtils.byteToUUID(rs.getBytes("voucher_id"));
            VoucherType voucherType = VoucherType.findVoucherType(rs.getString("voucher_type"));
            Long discountValue = rs.getLong("discount_value");
            UUID customerId = rs.getBytes("customer_id") != null ?
                    UuidUtils.byteToUUID(rs.getBytes("customer_id")) : null;
            LocalDateTime createdDate = rs.getTimestamp("created_date").toLocalDateTime();
            LocalDateTime modifiedDate = rs.getTimestamp("modified_date") != null ?
                    rs.getTimestamp("modified_date").toLocalDateTime() : null;
            return new Voucher(voucherId, voucherType, discountValue, customerId, createdDate, modifiedDate);
        };
        return rowMapper;
    }
}
