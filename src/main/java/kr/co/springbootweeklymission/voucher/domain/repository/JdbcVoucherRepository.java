package kr.co.springbootweeklymission.voucher.domain.repository;

import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.model.VoucherPolicy;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "" +
                "select * " +
                "from tbl_vouchers " +
                "where voucher_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    sql,
                    voucherRowMapper(),
                    voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "" +
                "select * " +
                "from tbl_vouchers";
        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public void update(Voucher voucher) {
        if (!voucher.isMember()) {
            String sql = "" +
                    "update tbl_vouchers set " +
                    "voucher_amount = ?, " +
                    "voucher_policy = ? " +
                    "where voucher_id = ?";
            jdbcTemplate.update(
                    sql,
                    voucher.getAmount(),
                    voucher.getVoucherPolicy().toString(),
                    voucher.getVoucherId().toString());
            return;
        }

        String sql = "" +
                "update tbl_vouchers set " +
                "voucher_amount = ?, " +
                "voucher_policy = ?, " +
                "member_id = ? " +
                "where voucher_id = ?";
        jdbcTemplate.update(
                sql,
                voucher.getAmount(),
                voucher.getVoucherPolicy().toString(),
                voucher.getMemberId().toString(),
                voucher.getVoucherId().toString());
    }

    @Override
    public void deleteById(UUID voucherId) {
        String sql = "" +
                "delete from tbl_vouchers " +
                "where voucher_id = ?";
        jdbcTemplate.update(sql, voucherId.toString());
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            if (rs.getString("member_id") == null) {
                return Voucher.builder()
                        .voucherId(UUID.fromString(rs.getString("voucher_id")))
                        .amount(rs.getInt("voucher_amount"))
                        .voucherPolicy(VoucherPolicy.valueOf(rs.getString("voucher_policy")))
                        .build();
            }

            return Voucher.builder()
                    .voucherId(UUID.fromString(rs.getString("voucher_id")))
                    .amount(rs.getInt("voucher_amount"))
                    .voucherPolicy(VoucherPolicy.valueOf(rs.getString("voucher_policy")))
                    .memberId(UUID.fromString(rs.getString("member_id")))
                    .build();
        };
    }
}
