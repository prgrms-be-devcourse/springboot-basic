package kr.co.programmers.springbootbasic.wallet.repository.impl;

import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.domain.impl.FixedAmountVoucher;
import kr.co.programmers.springbootbasic.voucher.domain.impl.PercentAmountVoucher;
import kr.co.programmers.springbootbasic.wallet.domain.Wallet;
import kr.co.programmers.springbootbasic.wallet.repository.WalletQuery;
import kr.co.programmers.springbootbasic.wallet.repository.WalletRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Profile("deploy")
public class JdbcWalletRepository implements WalletRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveVoucherInCustomerWallet(String voucherId, String walletId) {
        jdbcTemplate.update(WalletQuery.SAVE_VOUCHER_IN_WALLET,
                walletId,
                voucherId);
    }

    @Override
    public Wallet findAllVouchersById(String walletId) {
        List<Voucher> vouchers = jdbcTemplate.query(WalletQuery.FIND_ALL_VOUCHERS,
                voucherRowMapper(),
                walletId);
        UUID walletUUID = ApplicationUtils.toUUID(walletId.getBytes());

        return new Wallet(walletUUID, vouchers);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            var voucherId = ApplicationUtils.toUUID(rs.getBytes("id"));
            var type = VoucherType.resolveTypeId(rs.getInt("type_id"));
            var amount = rs.getLong("amount");
            var createdAt = rs.getTimestamp("created_at").toLocalDateTime();

            return switch (type) {
                case FIXED_AMOUNT -> new FixedAmountVoucher(voucherId, amount, createdAt);
                case PERCENT_AMOUNT -> new PercentAmountVoucher(voucherId, amount, createdAt);
            };
        };
    }
}
