package org.programers.vouchermanagement.wallet.domain;

import org.programers.vouchermanagement.member.domain.JdbcMemberRepository;
import org.programers.vouchermanagement.member.domain.Member;
import org.programers.vouchermanagement.voucher.domain.JdbcVoucherRepository;
import org.programers.vouchermanagement.voucher.domain.Voucher;
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
    private final JdbcVoucherRepository voucherRepository;
    private final JdbcMemberRepository memberRepository;

    public JdbcWalletRepository(DataSource dataSource, JdbcVoucherRepository voucherRepository, JdbcMemberRepository memberRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.voucherRepository = voucherRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Wallet save(Wallet wallet) {
        String sql = "insert into wallet (id, voucher_id, member_id) values (?, ?, ?)";
        jdbcTemplate.update(sql,
                wallet.getId().toString(),
                wallet.getVoucher().getId().toString(),
                wallet.getMember().getId().toString());
        return wallet;
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        String sql = "select * from wallet where id = ?";
        try {
            Wallet wallet = jdbcTemplate.queryForObject(sql, walletRowMapper(), id.toString());
            return Optional.of(wallet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findAllByVoucherId(UUID voucherId) {
        String sql = "select * from wallet where voucher_id = ?";
        return jdbcTemplate.query(sql, walletRowMapper(), voucherId.toString());
    }

    @Override
    public List<Wallet> findAllByMemberId(UUID memberId) {
        String sql = "select * from wallet where member_id = ?";
        return jdbcTemplate.query(sql, walletRowMapper(), memberId.toString());
    }

    @Override
    public List<Wallet> findAll() {
        String sql = "select * from wallet";
        return jdbcTemplate.query(sql, walletRowMapper());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "delete from wallet where id = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private RowMapper<Wallet> walletRowMapper() {
        return (rs, rowNum) -> {
            Voucher voucher = voucherRepository
                    .getById(UUID.fromString(rs.getString("voucher_id")));
            Member member = memberRepository
                    .getById(UUID.fromString(rs.getString("member_id")));
            return new Wallet(
                    UUID.fromString(rs.getString("id")), voucher, member);
        };
    }
}
