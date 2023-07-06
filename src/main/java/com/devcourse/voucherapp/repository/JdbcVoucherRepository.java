package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher(id, type, discount_amount) values (:id, :typeNumber, :discountAmount)";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", voucher.getId().toString())
                .addValue("typeNumber", voucher.getType().getNumber())
                .addValue("discountAmount", voucher.getDiscountAmount());

        template.update(sql, parameterSource);

        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        String sql = "select id, type, discount_amount from voucher";

        RowMapper<Voucher> voucherRowMapper = ((resultSet, rowNum) -> {
            String id = resultSet.getString("id");
            String typeNumber = resultSet.getString("type");
            int discountAmount = resultSet.getInt("discount_amount");

            return VoucherType.of(typeNumber)
                    .makeVoucher(UUID.fromString(id), String.valueOf(discountAmount));
        });

        return template.query(sql, voucherRowMapper);
    }
}
