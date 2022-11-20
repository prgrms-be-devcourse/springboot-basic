package prgms.vouchermanagementapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.VoucherEntity;

import javax.sql.DataSource;
import java.util.*;

@Component
@Profile("release | test")
public class JdbcVouchers {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcVouchers(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("vouchers");
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

    public List<VoucherEntity> findAllByCustomerName(String customerName) {
        String sql = "select * from vouchers " +
                "where customer_name=:customerName";

        try {
            Map<String, String> param = Map.of("customerName", customerName);
            return template.query(sql, param, voucherEntityRowMapper());
        } catch (DataAccessException e) {
            return Collections.emptyList();
        }
    }

    public Optional<VoucherEntity> findVoucherEntityById(String id) {
        String sql = "select * from vouchers " +
                "where id=:id";

        try {
            Map<String, String> param = Map.of("id", id);
            VoucherEntity voucherEntity = template.queryForObject(sql, param, voucherEntityRowMapper());
            return Optional.of(Objects.requireNonNull(voucherEntity));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public void updateFixedAmountVoucherById(String id, long amount) {
        String sql = "update vouchers " +
                "set amount=:amount " +
                "where id=:id";

        Map<String, Object> param = Map.of(
                "id", id,
                "amount", amount
        );

        template.update(sql, param);
    }

    public void updatePercentDiscountVoucherById(String id, long ratio) {
        String sql = "update vouchers " +
                "set ratio=:ratio " +
                "where id=:id";

        Map<String, Object> param = Map.of(
                "id", id,
                "ratio", ratio
        );

        template.update(sql, param);
    }

    public void deleteAll() {
        String sql = "delete from vouchers";
        Map<String, String> param = Collections.emptyMap();
        template.update(sql, param);
    }

    private RowMapper<VoucherEntity> voucherEntityRowMapper() {
        return ((rs, rowNum) -> {
            String uuid = rs.getString("id");
            String voucherType = rs.getString("type");
            Long amount = rs.getLong("amount");
            Long ratio = rs.getLong("ratio");
            String customerName = rs.getString("customer_name");
            return new VoucherEntity(uuid, voucherType, amount, ratio, customerName);
        });
    }
}
