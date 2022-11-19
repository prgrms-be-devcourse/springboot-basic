package prgms.vouchermanagementapp.storage;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.storage.entity.VoucherEntity;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class JdbcVouchers {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcVouchers(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("vouchers")
                .usingColumns("uuid", "voucher_type", "amount", "ratio", "customer_name");
    }

    public void save(VoucherEntity voucherEntity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(voucherEntity);
        jdbcInsert.execute(parameterSource);
    }

    public List<VoucherEntity> findAll() {
        String sql = "select * from vouchers";

        try {
            return template.query(sql, voucherEntityRowMapper());
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public List<VoucherEntity> findByCustomerName(String name) {
        String sql = "select * from vouchers where customer_name=:customerName";

        try {
            Map<String, String> param = Map.of("customerName", name);
            return template.query(sql, param, voucherEntityRowMapper());
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    private RowMapper<VoucherEntity> voucherEntityRowMapper() {
        return ((rs, rowNum) -> {
            String uuid = rs.getString("uuid");
            String voucherType = rs.getString("voucher_type");
            Long amount = rs.getLong("amount");
            Long ratio = rs.getLong("ratio");
            String customerName = rs.getString("customer_name");
            return new VoucherEntity(uuid, voucherType, amount, ratio, customerName);
        });
    }
}
