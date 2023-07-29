package prgms.spring_week1.domain.voucher.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.Dml;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl.Delete;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl.Insert;
import prgms.spring_week1.domain.util.sqlBuilder.builder.DmlBuilder.impl.Select;
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

    Column column = new Column.ColumnBuilder()
            .columns("voucher_id","voucher_type", "discount", "created_at")
            .build();

    Values values = new Values.ValuesBuilder()
            .values("UUID_TO_BIN(:voucherId)",":voucherType",":discount",":createdAt")
            .build();

    @Override
    public void insert(Voucher voucher) {
        Dml insertSql = new Insert.InsertBuilder()
                .insert(TableType.voucher)
                .columns(column)
                .values(values)
                .build();

        jdbcTemplate.update(insertSql.toString(), toParamMap(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        Dml findAllSql = new Select.SelectBuilder()
                .selectAll()
                .from(TableType.voucher)
                .build();

        return jdbcTemplate.query(findAllSql.toString(), voucherRowMapper);
    }

    Where where = new Where.WhereBuilder()
            .equal("voucher_type",":voucherType")
            .build();

    @Override
    public List<Voucher> findByType(String voucherType) {
        Dml findByTypeSql = new Select.SelectBuilder()
                .from(TableType.voucher)
                .where(where)
                .build();

        return jdbcTemplate.query(findByTypeSql.toString(), Collections.singletonMap("voucherType", voucherType), voucherRowMapper);
    }

    @Override
    public void delete() {
        Dml deleteSql = new Delete.DeleteBuilder()
                .delete(TableType.voucher)
                .build();

        jdbcTemplate.update(deleteSql.toString(), new HashMap<>());
    }
}
