package org.programmers.VoucherManagement.wallet.infrastructure;

import org.programmers.VoucherManagement.member.exception.MemberException;
import org.programmers.VoucherManagement.member.infrastructure.MemberReaderRepository;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.programmers.VoucherManagement.voucher.infrastructure.VoucherReaderRepository;
import org.programmers.VoucherManagement.wallet.domain.Wallet;
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

@Repository
@Primary
public class JdbcWalletReaderRepository implements WalletReaderRepository {
    private JdbcTemplate jdbcTemplate;
    private MemberReaderRepository memberReaderRepository;
    private VoucherReaderRepository voucherReaderRepository;

    public JdbcWalletReaderRepository(JdbcTemplate jdbcTemplate, MemberReaderRepository memberReaderRepository, VoucherReaderRepository voucherReaderRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.memberReaderRepository = memberReaderRepository;
        this.voucherReaderRepository = voucherReaderRepository;
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

    public RowMapper<Wallet> walletRowMapper() {
        return (result, rowNum) -> new Wallet(
                UUID.fromString(result.getString("wallet_id")),
                voucherReaderRepository.findById(UUID.fromString(result.getString("voucher_id"))).orElseThrow(() -> new VoucherException(NOT_FOUND_VOUCHER)),
                memberReaderRepository.findById(UUID.fromString(result.getString("member_id"))).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBER))
        );
    }
}
