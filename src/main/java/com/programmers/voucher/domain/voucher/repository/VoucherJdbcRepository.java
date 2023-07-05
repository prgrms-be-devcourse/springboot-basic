package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("db")
public class VoucherJdbcRepository implements VoucherRepository {
    private static final Logger LOG = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate template;

    public VoucherJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void save(Voucher voucher) {
        String sql = "insert into voucher(voucher_id, voucher_type, amount)" +
                " values(:voucherId, :voucherType, :amount)";
        VoucherDto voucherDto = voucher.toDto();
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherDto.getVoucherId().toString())
                .addValue("voucherType", voucherDto.getVoucherType().name())
                .addValue("amount", voucherDto.getAmount());

        int saved = template.update(sql, param);

        if (saved != 1) {
            DataAccessException ex = new IncorrectResultSizeDataAccessException(1, saved);
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

    @Override
    public void deleteAll() {
        String sql = "delete from voucher";
        template.update(sql, Map.of());
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "delete from voucher where voucher_id = :voucherId";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString());
        int deleted = template.update(sql, param);
        if (deleted != 1) {
            DataAccessException exception = new IncorrectResultSizeDataAccessException(1, deleted);
            LOG.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
            VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
            long amount = rs.getLong("amount");

            return voucherType.createVoucher(voucherId, amount);
        };
    }
}
