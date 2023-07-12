package com.programmers.springweekly.repository.voucher;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@Slf4j
@Profile({"local", "test"})
public class JdbcTemplateVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateVoucherRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into vouchers values(:voucherId, :discountAmount, :voucherType)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId())
                .addValue("discountAmount", voucher.getVoucherAmount())
                .addValue("voucherType", voucher.getVoucherType().toString());

        template.update(sql, param);

        return voucher;
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "update vouchers set voucher_discount_amount = :discountAmount, voucher_type = :voucherType where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId())
                .addValue("discountAmount", voucher.getVoucherAmount())
                .addValue("voucherType", voucher.getVoucherType().toString());

        template.update(sql, param);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from vouchers where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);
        try {
            Voucher voucher = template.queryForObject(sql, param, voucherRowMapper());

            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from vouchers";

        return template.query(sql, voucherRowMapper());
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "delete from vouchers where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);

        template.update(sql, param);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from vouchers";

        SqlParameterSource param = new MapSqlParameterSource();

        template.update(sql, param);
    }

    @Override
    public boolean existById(UUID voucherId) {
        String sql = "select * from vouchers where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);
        try {
            template.queryForObject(sql, param, voucherRowMapper());
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return ((resultSet, rowMap) ->
                VoucherFactory.createVoucher(UUID.fromString(resultSet.getString("voucher_id")),
                        VoucherType.valueOf(resultSet.getString("voucher_type")),
                        Long.parseLong(resultSet.getString("voucher_discount_amount"))
                )
        );
    }
}
