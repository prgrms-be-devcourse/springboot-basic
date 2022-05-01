package org.prgrms.kdt.domain.voucher.repository;

import org.prgrms.kdt.domain.voucher.exception.VoucherDataException;
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

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.prgrms.kdt.domain.common.exception.ExceptionType.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository{
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public UUID save(Voucher voucher) {
        int savedRows = jdbcTemplate.update(
                "INSERT INTO voucher(voucher_id, voucher_type, discount_value, customer_id, created_date, modified_date) " +
                        "VALUES (UNHEX(REPLACE(:voucherId, '-', '')), :voucherType, :discountValue, UNHEX(REPLACE(:customerId, '-', '')), :createdDate, :modifiedDate)",
                toParamMap(voucher));
        if(savedRows != 1){
            throw new VoucherDataException(NOT_SAVED);
        }
        return voucher.getVoucherId();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Voucher voucher = jdbcTemplate.queryForObject("SELECT * FROM voucher " +
                            "WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                    Collections.singletonMap("voucherId", UuidUtils.UuidToByte(voucherId)), voucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            logger.error("바우처 ID에 해당하는 데이터가 존재하지 않습니다.", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query("SELECT * FROM voucher " +
                        "WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                Collections.singletonMap("customerId", UuidUtils.UuidToByte(customerId)),
                voucherRowMapper());
    }

    @Override
    public List<Voucher> findByCustomerIdIsNull() {
        return jdbcTemplate.query("SELECT * FROM voucher " +
                        "WHERE customer_id IS NULL",
                Collections.emptyMap(),
                voucherRowMapper());
    }

    @Override
    public List<Voucher> findByTypeAndDate(VoucherType voucherType, LocalDate date) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("voucherType", voucherType.getType());
            put("createdDate", date.toString());
        }};
        return jdbcTemplate.query("SELECT * FROM voucher " +
                        "WHERE voucher_type = :voucherType AND DATE(created_date) = :createdDate",
                paramMap,
                voucherRowMapper());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM voucher", Collections.emptyMap(), voucherRowMapper());
    }

    @Override
    public int update(Voucher voucher) {
        return jdbcTemplate.update("UPDATE voucher " +
                        "SET voucher_type = :voucherType, discount_value = :discountValue, customer_id = UNHEX(REPLACE(:customerId, '-', '')), modified_date = :modifiedDate " +
                        "WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                toParamMap(voucher));
    }

    @Override
    public int updateCustomerId(UUID voucherId, UUID customerId) {
        Map<String, Object> paramMap = new HashMap<>() {{
            put("voucherId", UuidUtils.UuidToByte(voucherId));
            put("customerId", UuidUtils.UuidToByte(customerId));
            put("modifiedDate", LocalDateTime.now());
        }};
        return jdbcTemplate.update("UPDATE voucher " +
                        "SET customer_id = UNHEX(REPLACE(:customerId, '-', '')), modified_date = :modifiedDate " +
                        "WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                paramMap);
    }

    @Override
    public int deleteById(UUID voucherId) {
        return jdbcTemplate.update("DELETE FROM voucher " +
                        "WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                Collections.singletonMap("voucherId", UuidUtils.UuidToByte(voucherId)));
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE FROM voucher", Collections.emptyMap());
    }

    @Override
    public int deleteByCustomerId(UUID customerId) {
        return jdbcTemplate.update("DELETE FROM voucher " +
                        "WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                Collections.singletonMap("customerId", UuidUtils.UuidToByte(customerId)));
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", UuidUtils.UuidToByte(voucher.getVoucherId()));
            put("voucherType", voucher.getVoucherType().getType());
            put("discountValue", voucher.getDiscountValue());
            put("customerId", voucher.getCustomerId() == null
                    ? null : UuidUtils.UuidToByte(voucher.getCustomerId()));
            put("createdDate", voucher.getCreatedDateTime());
            put("modifiedDate", voucher.getModifiedDateTime());
        }};
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
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
    }
}
