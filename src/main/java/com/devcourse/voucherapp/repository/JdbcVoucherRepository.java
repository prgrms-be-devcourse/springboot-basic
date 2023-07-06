package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.VoucherType;
import com.devcourse.voucherapp.entity.voucher.Voucher;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into voucher(id, type, discount_amount) values (:voucherId, :typeNumber, :discountAmount)";
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(voucher);
        template.update(sql, parameterSource);

        return voucher;
    }

    @Override
    public List<Voucher> findAllVouchers() {
        String sql = "select id, type, discount_amount from voucher";

        RowMapper<Voucher> voucherRowMapper = ((resultSet, rowNum) -> {
            String voucherId = resultSet.getString("id");
            String typeNumber = resultSet.getString("type");
            int discountAmount = resultSet.getInt("discount_amount");

            return VoucherType.of(typeNumber)
                    .duplicateVoucher(voucherId, typeNumber, discountAmount);
        });

        return template.query(sql, voucherRowMapper);
    }
}
