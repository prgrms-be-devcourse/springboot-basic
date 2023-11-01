package com.programmers.springbootbasic.domain.voucher.infrastructure;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherRepository;
import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherCriteria;
import com.programmers.springbootbasic.util.SqlConverter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final String TABLE_NAME = "vouchers";
    private static final String ID = "id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String BENEFIT_VALUE = "benefit_value";
    private static final String CREATED_AT = "created_at";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Voucher> voucherRowMapper =
        (rs, rowNum) -> new Voucher(
            UUID.fromString(rs.getString(ID)),
            VoucherTypeEnum.of(rs.getString(VOUCHER_TYPE))
                .getVoucherType(rs.getInt(BENEFIT_VALUE)),
            rs.getInt(BENEFIT_VALUE),
            SqlConverter.toLocalDateTime(rs.getString(CREATED_AT))
        );

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        jdbcTemplate.update(
            String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", TABLE_NAME, ID,
                VOUCHER_TYPE, BENEFIT_VALUE, CREATED_AT),
            voucher.getId().toString(),
            voucher.getVoucherType().getVoucherTypeName(),
            voucher.getBenefitValue(),
            voucher.getCreatedAt().toString());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(String.format("SELECT * FROM %s", TABLE_NAME), voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return jdbcTemplate.query(String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, ID),
                voucherRowMapper, id.toString())
            .stream().findFirst();
    }

    @Override
    public int deleteById(UUID id) {
        return jdbcTemplate.update(String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, ID),
            id.toString());
    }

    @Override
    public int update(Voucher voucher) {
        return jdbcTemplate.update(
            String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, VOUCHER_TYPE,
                BENEFIT_VALUE, ID),
            voucher.getVoucherType().getVoucherTypeName(),
            voucher.getBenefitValue(),
            voucher.getId().toString());
    }
}
