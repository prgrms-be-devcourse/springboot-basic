package com.programmers.voucher.domain.voucher.repository;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.global.util.DataErrorMessages.INCORRECT_UPDATED_RESULT_SIZE;

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
                .addValue("voucherId", voucherDto.getCustomerId().toString())
                .addValue("voucherType", voucherDto.getVoucherType().name())
                .addValue("amount", voucherDto.getAmount());

        int saved = template.update(sql, param);

        if(saved != 1) {
            DataAccessException exception
                    = new IncorrectResultSizeDataAccessException(INCORRECT_UPDATED_RESULT_SIZE, 1, saved);
            LOG.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        return template.query(sql, voucherRowMapper());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            UUID customerId = UUID.fromString(rs.getString("voucher_id"));
            VoucherType voucherType = VoucherType.valueOf(rs.getString("voucher_type"));
            long amount = rs.getLong("amount");

            return voucherType.createVoucher(customerId, amount);
        };
    }
}
