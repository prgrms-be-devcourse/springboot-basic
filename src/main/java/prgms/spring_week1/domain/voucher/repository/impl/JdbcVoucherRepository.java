package prgms.spring_week1.domain.voucher.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.domain.voucher.repository.impl.sql.VoucherManageSql;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JdbcVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

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
        var update = jdbcTemplate.update(VoucherManageSql.insertNewVoucherSQL,
                toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("추가된 바우처가 없습니다.");
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return jdbcTemplate.query(VoucherManageSql.findAllVoucherSQL, voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("조회된 바우처 리스트가 없습니다.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Voucher> findByType(String voucherType) {
        try {
            return jdbcTemplate.query(VoucherManageSql.findVoucherByTypeSQL,
                    Collections.singletonMap("voucherType", voucherType),
                    voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("조회된 바우처 리스트가 없습니다.", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void delete() {
        jdbcTemplate.update(VoucherManageSql.deleteAllVoucherSQL, new HashMap<>());
    }
}
