package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.global.util.DataAccessConstants.UPDATE_ONE;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {
    private static final Logger LOG = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate template;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Voucher voucher) {
        VoucherDto voucherDto = VoucherDto.from(voucher);
        String sql = "insert into voucher(voucher_id, voucher_type, amount, created_at)" +
                " values(:voucherId, :voucherType, :amount, :createdAt)";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherDto.getVoucherId().toString())
                .addValue("voucherType", voucherDto.getVoucherType().name())
                .addValue("amount", voucherDto.getAmount())
                .addValue("createdAt", Timestamp.valueOf(voucherDto.getCreatedAt()));

        int saved = template.update(sql, param);
        if (saved != UPDATE_ONE) {
            DataAccessException ex = new IncorrectResultSizeDataAccessException(UPDATE_ONE, saved);
            LOG.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        return template.query(sql, voucherRowMapper());
    }

    @Override
    public List<Voucher> findAll(VoucherType voucherType,
                                 LocalDateTime startTime, LocalDateTime endTime) {
        String sql = createSearchQuery(voucherType, startTime, endTime);
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherType", voucherType != null ? voucherType.toString() : null)
                .addValue("startTime", startTime != null ? Timestamp.valueOf(startTime) : null)
                .addValue("endTime", endTime != null ? Timestamp.valueOf(endTime) : null);
        return template.query(sql, param, voucherRowMapper());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from voucher where voucher_id = :voucherId";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);

        try {
            Voucher voucher = template.queryForObject(sql, param, voucherRowMapper());
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    private String createSearchQuery(VoucherType voucherType, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "select * from voucher";
        boolean multipleCondition = false;
        if(voucherType != null || (startTime != null && endTime != null)) {
            sql += " where";
        }
        if(voucherType != null) {
            sql += " voucher_type = :voucherType";
            multipleCondition = true;
        }
        if(startTime != null && endTime != null) {
            if(multipleCondition) {
                sql += " and";
            }
            sql += " created_at between :startTime and :endTime";
        }
        return sql;
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "delete from voucher where voucher_id = :voucherId";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString());

        int deleted = template.update(sql, param);
        if (deleted != UPDATE_ONE) {
            DataAccessException exception = new IncorrectResultSizeDataAccessException(UPDATE_ONE, deleted);
            LOG.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
            VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
            long amount = rs.getLong("amount");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            return voucherType.retrieveVoucher(voucherId, amount, createdAt);
        };
    }
}
