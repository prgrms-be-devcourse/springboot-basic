package com.prgms.vouchermanager.repository.voucher;

import com.prgms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.domain.voucher.VoucherType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.prgms.vouchermanager.repository.voucher.VoucherQueryType.*;


@Repository
@Slf4j
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate template;

    public JdbcVoucherRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", voucher.getId().toString())
                .addValue("discount_value", voucher.getDiscountValue())
                .addValue("type", voucher.getVoucherType().name());


        template.update(INSERT.getQuery(), param);

        return voucher;

    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id.toString());
        try {
            return Optional.of(template.queryForObject(SELECT_BY_ID.getQuery(), param, voucherRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {

        return template.query(SELECT_ALL.getQuery(), voucherRowMapper());

    }

    @Override
    public void update(Voucher voucher) { //항상 존재하는 id에만 실행 가능하도록 전제가 있다.

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", voucher.getId().toString())
                .addValue("discount_value", voucher.getDiscountValue())
                .addValue("type", voucher.getVoucherType().name());

        template.update(UPDATE.getQuery(), param);
    }

    @Override
    public void deleteById(UUID id) {
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id.toString());

        template.update(DELETE_BY_ID.getQuery(), param);

    }

    @Override
    public void deleteAll() {
        template.update(DELETE_ALL.getQuery(), new MapSqlParameterSource());
    }


    private  RowMapper<Voucher> voucherRowMapper() {
        return (rs, count) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            VoucherType type = VoucherType.valueOf(rs.getString("type"));
            Long discountValue = rs.getLong("discount_value");

            return type == VoucherType.FIXED_AMOUNT ?
                    new FixedAmountVoucher(id, discountValue) :
                    new PercentDiscountVoucher(id, discountValue);
        };
    }


}
