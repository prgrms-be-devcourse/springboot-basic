package prgms.vouchermanagementapp.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.FixedAmountVoucher;
import prgms.vouchermanagementapp.domain.PercentDiscountVoucher;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Profile({"jdbc", "release", "test"})
public class JdbcVoucherRepository implements VoucherRepository {

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
        String sql = "select * from vouchers " +
                "where voucher_id=:voucher_id";

        try {
            Map<String, String> param = Map.of("voucher_id", voucherId.toString());
            Voucher voucher = template.queryForObject(sql, param, voucherRowMapper());
            return Optional.of(Objects.requireNonNull(voucher));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateVoucher(UUID voucherId, long fixedDiscountLevel) {
        // TODO: 업데이트 기능 추가
        throw new UnsupportedOperationException();
    }

//    public Optional<VoucherEntity> findVoucherEntityById(String id) {
//        String sql = "select * from vouchers " +
//                "where id=:id";
//
//        try {
//            Map<String, String> param = Map.of("id", id);
//            VoucherEntity voucherEntity = template.queryForObject(sql, param, voucherRowMapper());
//            return Optional.of(Objects.requireNonNull(voucherEntity));
//        } catch (DataAccessException dataAccessException) {
//            return Optional.empty();
//        }
//    }

//    public void updateFixedAmountVoucherById(String id, long amount) {
//        String sql = "update vouchers " +
//                "set amount=:amount " +
//                "where id=:id";
//
//        Map<String, Object> param = Map.of(
//                "id", id,
//                "amount", amount
//        );
//
//        template.update(sql, param);
//    }
//
//    public void updatePercentDiscountVoucherById(String id, long ratio) {
//        String sql = "update vouchers " +
//                "set ratio=:ratio " +
//                "where id=:id";
//
//        Map<String, Object> param = Map.of(
//                "id", id,
//                "ratio", ratio
//        );
//
//        template.update(sql, param);
//    }

    public void deleteAll() {
        String sql = "delete from voucher";
        Map<String, String> param = Collections.emptyMap();
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
