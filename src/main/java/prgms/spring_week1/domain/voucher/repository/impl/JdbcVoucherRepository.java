package prgms.spring_week1.domain.voucher.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Delete;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Insert;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Select;
import prgms.spring_week1.domain.util.sqlBuilder.builder.conditionBuilder.Where;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Column;
import prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder.Values;
import prgms.spring_week1.domain.util.type.TableType;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcVoucherRepository implements VoucherRepository {
    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Voucher> voucherRowMapper = (resultset, i) -> {
        var voucherType = VoucherType.valueOf(resultset.getString("voucher_type"));
        var discount = resultset.getInt("discount");
        return new Voucher(voucherType, discount);
    };

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return Map.of(
                "voucherId", voucher.getVoucherId().toString().getBytes(),
                "voucherType", String.valueOf(voucher.getVoucherType()),
                "discount", voucher.getDiscount(),
                "createdAt", voucher.getCreatedAt()
        );
    }

    @Override
    public void insert(Voucher voucher) {
        Insert insertSql = Insert.builder()
                .insert(TableType.voucher)
                .into(Column
                        .builder()
                        .columns("voucher_id", "voucher_type", "discount", "created_at")
                        .build())
                .values(Values
                        .builder()
                        .values("UUID_TO_BIN(:voucherId)", ":voucherType", ":discount", ":createdAt")
                        .build())
                .build();

        jdbcTemplate.update(insertSql.getQuery(), toParamMap(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        Select findAllSql = Select.builder()
                .selectAll()
                .from(TableType.voucher)
                .build();

        return jdbcTemplate.query(findAllSql.getQuery(), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        Select findByTypeSql = Select.builder()
                .select(Column
                        .builder()
                        .columns("voucher_type", "discount")
                        .build())
                .from(TableType.voucher)
                .where(Where
                        .builder()
                        .where("voucher_type", ":voucherType")
                        .build())
                .build();

        return jdbcTemplate.query(findByTypeSql.getQuery(), Collections.singletonMap("voucherType", voucherType), voucherRowMapper);
    }

    @Override
    public void delete() {
        Delete deleteSql = Delete
                .builder()
                .deleteFrom(TableType.voucher)
                .build();

        jdbcTemplate.update(deleteSql.getQuery(), new HashMap<>());
    }
}
