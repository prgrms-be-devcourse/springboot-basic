package prgms.spring_week1.domain.voucher.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.util.SqlBuilder;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.domain.voucher.repository.impl.sql.VoucherManageSql;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JdbcVoucherRepository implements VoucherRepository {
    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final int VALID_ROW_RESULT = 1;

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
        String insertSql = new SqlBuilder.InsertBuilder()
                .insert("voucher")
                .columns("voucher_id","voucher_type", "discount", "created_at")
                .values("UUID_TO_BIN(:voucherId)",":voucherType",":discount",":createdAt")
                .build();


        jdbcTemplate.update(insertSql, toParamMap(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        String findAllSql = new SqlBuilder.SelectBuilder()
                    .select("*")
                    .from("voucher")
                    .build();

        return jdbcTemplate.query(findAllSql, voucherRowMapper);
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        String findByTypeSql = new SqlBuilder.SelectBuilder()
                .select("*")
                .from("voucher")
                .where("voucher_type = :voucherType")
                .build();

        return jdbcTemplate.query(findByTypeSql, Collections.singletonMap("voucherType", voucherType), voucherRowMapper);
    }

    @Override
    public void delete() {
        String deleteSql = new SqlBuilder.DeleteBuilder()
                .delete("voucher")
                .build();
        jdbcTemplate.update(deleteSql, new HashMap<>());
    }
}
