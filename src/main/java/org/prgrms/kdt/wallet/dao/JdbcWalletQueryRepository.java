package org.prgrms.kdt.wallet.dao;

import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.DiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.wallet.domain.QueryWallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class JdbcWalletQueryRepository implements WalletQueryRepository {
    private static final RowMapper<QueryWallet> QueryWalletRowMapper = (resultSet, i) -> {
        UUID walletId = UUID.fromString(resultSet.getString("W.id"));

        UUID memberId = UUID.fromString(resultSet.getString("W.member_id"));
        String memberName = resultSet.getString("M.name");
        MemberStatus memberStatus = MemberStatus.getStatus(resultSet.getString("M.status"));

        UUID voucherId = UUID.fromString(resultSet.getString("W.voucher_id"));
        VoucherType voucherType = VoucherType.getTypeByStr(resultSet.getString("V.type"));
        DiscountPolicy discountPolicy = voucherType.createPolicy(resultSet.getDouble("V.amount"));
        LocalDateTime createdAt = resultSet.getTimestamp("V.created_at").toLocalDateTime();

        Member member = new Member(memberId, memberName, memberStatus);
        Voucher voucher = new Voucher(voucherId, voucherType, discountPolicy, createdAt);
        return new QueryWallet(walletId, member, voucher);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletQueryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<QueryWallet> findWithMemeberAndVoucherByMemberId(UUID memberId) {
        String sql = "select W.id, W.member_id, M.name, M.status, W.voucher_id, V.type, V.amount, V.created_at from wallet W " +
                "INNER JOIN member M ON W.member_id = M.id " +
                "INNER JOIN voucher V ON W.voucher_id = V.id " +
                "WHERE W.member_id = ?";
        return jdbcTemplate.query(sql, QueryWalletRowMapper, memberId.toString());
    }

    @Override
    public List<QueryWallet> findWithMemeberAndVoucherByVoucherId(UUID voucherId) {
        String sql = "select W.id, W.member_id, M.name, M.status, W.voucher_id, V.type, V.amount, V.created_at from wallet W " +
                "INNER JOIN member M ON W.member_id = M.id " +
                "INNER JOIN voucher V ON W.voucher_id = V.id " +
                "WHERE W.voucher_id = ?";
        return jdbcTemplate.query(sql, QueryWalletRowMapper, voucherId.toString());
    }

    @Override
    public List<QueryWallet> findWithMemeberAndVoucherAll() {
        String sql = "select W.id, W.member_id, M.name, M.status, W.voucher_id, V.type, V.amount, V.created_at from wallet W " +
                "INNER JOIN member M ON W.member_id = M.id " +
                "INNER JOIN voucher V ON W.voucher_id = V.id";
        return jdbcTemplate.query(sql, QueryWalletRowMapper);
    }
}