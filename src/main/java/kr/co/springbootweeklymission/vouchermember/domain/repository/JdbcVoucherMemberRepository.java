package kr.co.springbootweeklymission.vouchermember.domain.repository;

import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.JdbcMemberRepository;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.JdbcVoucherRepository;
import kr.co.springbootweeklymission.vouchermember.domain.entity.VoucherMember;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

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
                voucherMember.getVoucherMemberId().toString(),
                voucherMember.getVoucher().getVoucherId().toString(),
                voucherMember.getMember().getMemberId().toString()
        );
        return voucherMember;
    }

    @Override
    public List<VoucherMember> findVouchersMembersByMemberId(UUID memberId) {
        String sql = "" +
                "select * " +
                "from tbl_vouchers_members " +
                "where member_id = ?";
        return jdbcTemplate.query(sql, voucherMemberRowMapper(), memberId.toString());
    }

    @Override
    public List<VoucherMember> findVouchersMembersByVoucherId(UUID voucherId) {
        String sql = "" +
                "select * " +
                "from tbl_vouchers_members " +
                "where voucher_id = ?";
        return jdbcTemplate.query(sql, voucherMemberRowMapper(), voucherId.toString());
    }

    @Override
    public void deleteByVoucherIdAndMemberId(UUID voucherId,
                                             UUID memberId) {
        String sql = "" +
                "delete from tbl_vouchers_members " +
                "where voucher_id = ? " +
                "and member_id = ?";
        jdbcTemplate.update(sql, voucherId.toString(), memberId.toString());
    }

    private RowMapper<VoucherMember> voucherMemberRowMapper() {
        return (rs, rowNum) -> {
            String voucherSql = "" +
                    "select * " +
                    "from tbl_vouchers " +
                    "where voucher_id = ?";
            Voucher voucher = jdbcTemplate.queryForObject(
                    voucherSql,
                    JdbcVoucherRepository.voucherRowMapper(),
                    rs.getString("voucher_id")
            );

            String memberSql = "" +
                    "select * " +
                    "from tbl_members " +
                    "where member_id = ?";
            Member member = jdbcTemplate.queryForObject(
                    memberSql,
                    JdbcMemberRepository.memberRowMapper(),
                    rs.getString("member_id")
            );

            return VoucherMember.builder()
                    .voucherMemberId(UUID.fromString(rs.getString("voucher_member_id")))
                    .voucher(voucher)
                    .member(member)
                    .build();
        };
    }
}
