package kr.co.springbootweeklymission.vouchermember.domain.repository;

import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcVoucherMemberRepository implements VoucherMemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public VoucherMember save(VoucherMember voucherMember) {
        String sql = "" +
                "insert into tbl_vouchers_members (voucher_member_id, voucher_id, member_id) " +
                "values (?, ?, ?)";
        jdbcTemplate.update(
                sql,
                voucherMember.getVoucherMemberId(),
                voucherMember.getVoucher().getVoucherId(),
                voucherMember.getMember().getMemberId()
        );
        return voucherMember;
    }
}
