package org.prgrms.kdt.wallet.dao;

import org.prgrms.kdt.exception.NotUpdateException;
import org.prgrms.kdt.member.domain.Member;
import org.prgrms.kdt.member.domain.MemberStatus;
import org.prgrms.kdt.voucher.domain.DiscountPolicy;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.wallet.domain.Wallet;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcWalletRepository implements WalletRepository {
    private static final RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        UUID walletId = UUID.fromString(resultSet.getString("id"));

        UUID memberId = UUID.fromString(resultSet.getString("member_id"));
        String memberName = resultSet.getString("name");
        MemberStatus memberStatus = MemberStatus.getStatus(resultSet.getString("status"));

        UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
        VoucherType voucherType = VoucherType.getTypeByStr(resultSet.getString("type"));
        DiscountPolicy discountPolicy = voucherType.createPolicy(resultSet.getDouble("amount"));

        Member member = new Member(memberId, memberName, memberStatus);
        Voucher voucher = new Voucher(voucherId, voucherType, discountPolicy);
        return new Wallet(walletId, member, voucher);
    };

    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Wallet insert(Wallet wallet) {
        String sql = "INSERT INTO wallet(id, member_id, voucher_id) VALUES (?, ?, ?)";
        int update = jdbcTemplate.update(sql, wallet.getWalletId().toString(),
                wallet.getMember().getMemberId().toString(),
                wallet.getVoucher().getVoucherId().toString());
        if (update != 1) {
            throw new NotUpdateException("db에 insert가 수행되지 못했습니다.");
        }
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        String sql = "select * from wallet A " +
                "LEFT JOIN member B ON A.member_id = B.id " +
                "LEFT JOIN voucher C ON A.voucher_id = C.id " +
                "WHERE A.id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, walletRowMapper, walletId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findByMemberId(UUID memberId) {
        String sql = "select * from wallet A " +
                "LEFT JOIN member B ON A.member_id = B.id " +
                "LEFT JOIN voucher C ON A.voucher_id = C.id " +
                "WHERE A.member_id = ?";
        return jdbcTemplate.query(sql, walletRowMapper, memberId.toString());
    }

    @Override
    public List<Wallet> findByVoucherId(UUID voucherId) {
        String sql = "select * from wallet A " +
                "LEFT JOIN member B ON A.member_id = B.id " +
                "LEFT JOIN voucher C ON A.voucher_id = C.id " +
                "WHERE A.voucher_id = ?";
        return jdbcTemplate.query(sql, walletRowMapper, voucherId.toString());
    }

    @Override
    public void deleteById(UUID walletId) {
        String sql = "DELETE FROM wallet WHERE id = ?";
        jdbcTemplate.update(sql, walletId.toString());
    }

    @Override
    public List<Wallet> findAll() {
        String sql = "select * from wallet A " +
                "LEFT JOIN member B ON A.member_id = B.id " +
                "LEFT JOIN voucher C ON A.voucher_id = C.id";
        return jdbcTemplate.query(sql, walletRowMapper);
    }
}