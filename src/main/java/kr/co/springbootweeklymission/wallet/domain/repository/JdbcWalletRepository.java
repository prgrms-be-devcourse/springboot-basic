package kr.co.springbootweeklymission.wallet.domain.repository;

import kr.co.springbootweeklymission.global.error.exception.DuplicatedException;
import kr.co.springbootweeklymission.global.response.ResponseStatus;
import kr.co.springbootweeklymission.member.domain.entity.Member;
import kr.co.springbootweeklymission.member.domain.repository.JdbcMemberRepository;
import kr.co.springbootweeklymission.voucher.domain.entity.Voucher;
import kr.co.springbootweeklymission.voucher.domain.repository.JdbcVoucherRepository;
import kr.co.springbootweeklymission.wallet.domain.entity.Wallet;
import org.springframework.dao.DuplicateKeyException;
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
    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Wallet save(Wallet wallet) {
        String sql = "" +
                "insert into tbl_wallets (wallet_id, voucher_id, member_id) " +
                "values (?, ?, ?)";
        try {
            jdbcTemplate.update(
                    sql,
                    wallet.getWalletId().toString(),
                    wallet.getVoucher().getVoucherId().toString(),
                    wallet.getMember().getMemberId().toString()
            );
        } catch (DuplicateKeyException e) {
            throw new DuplicatedException(ResponseStatus.FAIL_DUPLICATED_KEY);
        }
        return wallet;
    }

    @Override
    public List<Wallet> findAllByMemberId(UUID memberId) {
        String sql = "" +
                "select * " +
                "from tbl_wallets " +
                "where member_id = ?";
        return jdbcTemplate.query(sql, walletRowMapper(), memberId.toString());
    }

    @Override
    public Optional<Wallet> findByVoucherId(UUID voucherId) {
        String sql = "" +
                "select * " +
                "from tbl_wallets " +
                "where voucher_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    sql,
                    walletRowMapper(),
                    voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByVoucherIdAndMemberId(UUID voucherId,
                                             UUID memberId) {
        String sql = "" +
                "delete from tbl_wallets " +
                "where voucher_id = ? " +
                "and member_id = ?";
        jdbcTemplate.update(sql, voucherId.toString(), memberId.toString());
    }

    private RowMapper<Wallet> walletRowMapper() {
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

            return Wallet.builder()
                    .walletId(UUID.fromString(rs.getString("wallet_id")))
                    .voucher(voucher)
                    .member(member)
                    .build();
        };
    }
}
