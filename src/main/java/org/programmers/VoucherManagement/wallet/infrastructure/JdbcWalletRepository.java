package org.programmers.VoucherManagement.wallet.infrastructure;

import org.programmers.VoucherManagement.member.exception.MemberException;
import org.programmers.VoucherManagement.member.infrastructure.MemberRepository;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherRepository;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
import org.programmers.VoucherManagement.wallet.exception.WalletException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programmers.VoucherManagement.member.exception.MemberExceptionMessage.NOT_FOUND_MEMBER;
import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.NOT_FOUND_VOUCHER;
import static org.programmers.VoucherManagement.wallet.exception.WalletExceptionMessage.FAIL_TO_DELETE;
import static org.programmers.VoucherManagement.wallet.exception.WalletExceptionMessage.FAIL_TO_INSERT;

@Repository
@Primary
public class JdbcWalletRepository implements WalletRepository {
    private JdbcTemplate jdbcTemplate;
    private MemberRepository memberRepository;
    private VoucherRepository voucherRepository;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate, MemberRepository memberRepository, VoucherRepository voucherRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.memberRepository = memberRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Wallet insert(Wallet wallet) {
        String sql = "insert into wallet_table(wallet_id, voucher_id, member_id) values (?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                wallet.getWalletId().toString(),
                wallet.getVoucher().getVoucherId().toString(),
                wallet.getMember().getMemberUUID().toString());
        if (insertCount != 1) {
            throw new WalletException(FAIL_TO_INSERT);
        }
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(UUID walletId) {
        String sql = "select wallet_id, voucher_id, member_id from wallet_table where wallet_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    walletRowMapper(),
                    walletId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findAllByMemberId(UUID memberId) {
        String sql = "select wallet_id, voucher_id, member_id from wallet_table where member_id = ?";

        return jdbcTemplate.query(sql, walletRowMapper(), memberId.toString());
    }

    @Override
    public List<Wallet> findAllByVoucherId(UUID voucherId) {
        String sql = "select wallet_id, voucher_id, member_id from wallet_table where voucher_id = ?";

        return jdbcTemplate.query(sql, walletRowMapper(), voucherId.toString());
    }

    @Override
    public void delete(UUID walletId) {
        String sql = "delete from wallet_table where wallet_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                walletId.toString());
        if (deleteCount != 1) {
            throw new WalletException(FAIL_TO_DELETE);
        }
    }

    public RowMapper<Wallet> walletRowMapper() {
        return (result, rowNum) -> new Wallet(
                UUID.fromString(result.getString("wallet_id")),
                voucherRepository.findById(UUID.fromString(result.getString("voucher_id"))).orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER)),
                memberRepository.findById(UUID.fromString(result.getString("member_id"))).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER))
        );
    }
}
