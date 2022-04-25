package org.prgrms.voucherapplication.repository.voucher.jdbc;

import org.prgrms.voucherapplication.entity.SqlVoucher;
import org.prgrms.voucherapplication.exception.DataNotInsertedException;
import org.prgrms.voucherapplication.exception.DataNotUpdatedException;
import org.prgrms.voucherapplication.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcVoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SELECT_ALL_SQL = "SELECT * FROM vouchers";
    private final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, voucher_type, discount_amount, voucher_owner, is_issued, created_at, issued_at) VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discountAmount, UUID_TO_BIN(:voucherOwner), :isIssued, :createdAt, :issuedAt)";
    private final String SELECT_BY_ID_SQL = "select * from vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String SELECT_BY_IS_ISSUED_SQL = "select * from vouchers WHERE is_issued = :isIssued";
    private final String SELECT_BY_VOUCHER_OWNER_SQL = "select * from vouchers WHERE voucher_owner = UUID_TO_BIN(:voucherOwner)";
    private final String UPDATE_SQL = "UPDATE vouchers SET voucher_owner = UUID_TO_BIN(:voucherOwner), is_issued = :isIssued, issued_at = :issuedAt WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String DELETE_BY_ID_SQL = "DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String DELETE_ALL_SQL = "DELETE FROM vouchers";

    private static final RowMapper<SqlVoucher> sqlVoucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = Util.toUUID(resultSet.getBytes("voucher_id"));
        String voucherType = resultSet.getString("voucher_type");
        long discountAmount = resultSet.getLong("discount_amount");
        UUID voucherOwner = resultSet.getBytes("voucher_owner") != null ?
                Util.toUUID(resultSet.getBytes("voucher_owner")) : null;
        boolean isIssued = resultSet.getBoolean("is_issued");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime issuedAt = resultSet.getTimestamp("issued_at") != null ?
                resultSet.getTimestamp("issued_at").toLocalDateTime() : null;

        return new SqlVoucher(voucherId, voucherType, discountAmount, voucherOwner, isIssued, createdAt, issuedAt);
    };

    private Map<String, Object> toParamMap (SqlVoucher voucher) {
        return new HashMap<>(){{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", voucher.getVoucherType());
            put("discountAmount", voucher.getDiscountAmount());
            put("voucherOwner", voucher.getVoucherOwner() != null ? voucher.getVoucherOwner().toString().getBytes() : null);
            put("isIssued",  voucher.isIssued());
            put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
            put("issuedAt", voucher.getVoucherOwner() != null ? Timestamp.valueOf(voucher.getIssuedAt()) : null);
        }};
    }

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public SqlVoucher insert(SqlVoucher voucher) {
        int update = jdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
        if (update != 1) {
            throw new DataNotInsertedException();
        }

        return voucher;
    }

    public List<SqlVoucher> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, sqlVoucherRowMapper);
    }

    public Optional<SqlVoucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            sqlVoucherRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    public Optional<List<SqlVoucher>> findByIsIssued(boolean isIssued) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.query(SELECT_BY_IS_ISSUED_SQL,
                            Collections.singletonMap("isIssued", isIssued),
                            sqlVoucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    public Optional<List<SqlVoucher>> findByVoucherOwner(UUID voucherOwner) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.query(SELECT_BY_VOUCHER_OWNER_SQL,
                            Collections.singletonMap("voucherOwner", voucherOwner.toString().getBytes()),
                            sqlVoucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    public SqlVoucher update(SqlVoucher voucher) {
        int update = jdbcTemplate.update(UPDATE_SQL, toParamMap(voucher));
        if (update != 1) {
            throw new DataNotUpdatedException();
        }
        return voucher;
    }

    public void deleteById(UUID voucherId) {
        jdbcTemplate.update(DELETE_BY_ID_SQL, Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
    }

    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }
}
