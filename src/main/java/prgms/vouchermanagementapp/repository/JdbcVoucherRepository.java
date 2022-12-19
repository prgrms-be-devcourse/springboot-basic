package prgms.vouchermanagementapp.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.model.FixedAmountVoucher;
import prgms.vouchermanagementapp.model.PercentDiscountVoucher;
import prgms.vouchermanagementapp.model.Voucher;
import prgms.vouchermanagementapp.model.value.Amount;
import prgms.vouchermanagementapp.model.value.Ratio;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Profile({"jdbc", "release", "test"})
public class JdbcVoucherRepository implements VoucherRepository {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("voucher")
                .usingColumns("voucher_id", "voucher_type", "discount_level", "created_date_time");
    }

    @Override
    public void save(Voucher voucher) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(voucher);
        jdbcInsert.execute(parameterSource);
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";

        try {
            return template.query(sql, voucherRowMapper());
        } catch (DataAccessException dataAccessException) {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from voucher " +
                "where voucher_id=:voucherId";

        try {
            Map<String, String> param = Map.of("voucherId", voucherId.toString());
            Voucher voucher = template.queryForObject(sql, param, voucherRowMapper());
            return Optional.of(Objects.requireNonNull(voucher));
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            return Optional.empty();
        }
    }

    @Override
    public void updateDiscountLevel(UUID voucherId, long discountLevel) {
        String sql = "update voucher " +
                "set discount_level=:discountLevel " +
                "where voucher_id=:voucherId";

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString())
                .addValue("discountLevel", discountLevel);

        template.update(sql, parameterSource);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from voucher";
        Map<String, String> param = Collections.emptyMap();
        template.update(sql, param);
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "delete from voucher " +
                "where voucher_id=:voucherId";

        Map<String, String> param = Map.of("voucherId", voucherId.toString());

        template.update(sql, param);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return ((rs, rowNum) -> {
            UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
            String voucherType = rs.getString("voucher_type");
            long discountLevel = rs.getLong("discount_level");
            LocalDateTime createdDateTime = rs.getTimestamp("created_date_time").toLocalDateTime();

            return switch (voucherType) {
                case "FixedAmountVoucher" ->
                        new FixedAmountVoucher(voucherId, new Amount(discountLevel), createdDateTime);
                case "PercentDiscountVoucher" ->
                        new PercentDiscountVoucher(voucherId, new Ratio(discountLevel), createdDateTime);
                default -> throw new IllegalArgumentException(
                        MessageFormat.format("Invalid voucher type: ''{0}''", voucherType)
                );
            };
        });
    }
}
