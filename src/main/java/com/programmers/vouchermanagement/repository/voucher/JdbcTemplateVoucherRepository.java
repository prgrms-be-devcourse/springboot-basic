package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.domain.voucher.FixedAmountVoucher;
import com.programmers.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JdbcTemplateVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate template;

    @Autowired
    public JdbcTemplateVoucherRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Voucher voucher) {
        String sql = "INSERT INTO vouchers (type, amount) values (:type, :amount)";

        SqlParameterSource params = new MapSqlParameterSource().addValue("type", voucher.getType().toString()).addValue("amount", voucher.getAmount());

        template.update(sql, params);
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM vouchers";

        return template.query(sql, (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            VoucherType type = VoucherType.valueOf(rs.getString("type"));
            long amount = rs.getLong("amount");

            switch (type) {
                case FIXED_AMOUNT:
                    return new FixedAmountVoucher(id, amount);
                case PERCENT_DISCOUNT:
                    return new PercentDiscountVoucher(id, amount);
                default:
                    throw new IllegalArgumentException("Unknown VoucherType: " + type);
            }
        });
    }
}
