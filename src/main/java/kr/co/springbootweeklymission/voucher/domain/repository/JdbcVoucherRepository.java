package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "" +
                "insert into tbl_vouchers (voucher_id, voucher_amount, voucher_policy) " +
                "values (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                voucher.getVoucherId().toString(),
                voucher.getAmount(),
                voucher.getVoucherPolicy().toString());
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "" +
                "select * " +
                "from tbl_vouchers";
        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> Voucher.builder()
                .voucherId(UUID.fromString(rs.getString("voucher_id")))
                .amount(rs.getInt("voucher_amount"))
                .voucherPolicy(VoucherPolicy.valueOf(rs.getString("voucher_policy")))
                .build();
    }
}
