package org.prgrms.kdt.wallet;

import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherGenerator;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.prgrms.kdt.voucher.util.Util.toUUID;

@Repository
public class WalletJdbcWalletRepository implements WalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public WalletJdbcWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static RowMapper<Wallet> walletRowMapper = (resultSet, i) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var walletId = toUUID(resultSet.getBytes("wallet_id"));

        return new Wallet(walletId, customerId);
    };

    @Override
    public List<Wallet> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query(
                "select * from wallet where customer_id = UUID_TO_BIN(?)",
                walletRowMapper,
                customerId.toString().getBytes());
    }

    @Override
    public List<Wallet> findByWalletId(UUID walletId) {
        return (jdbcTemplate.query(
                "select * from wallet where wallet_id = UUID_TO_BIN(?)",
                walletRowMapper,
                walletId.toString().getBytes()));
    }

    public int insert(UUID walletId, UUID customerId) {
        var update = jdbcTemplate.update("INSERT INTO wallet values(UUID_TO_BIN(?) , UUID_TO_BIN(?))",
                walletId.toString().getBytes(),
                customerId.toString().getBytes());
        return update;
    }

    public List<Wallet> findAll() {
        return jdbcTemplate.query("select * from wallet", walletRowMapper);
    }

    //walletList 로 바우처 리스트 조회
    @Override
    public List<Voucher> findVouchersByWalletList(List<byte[]> wallets) {
        String sql = String.join(",", Collections.nCopies(wallets.size(), " UUID_TO_BIN(?)"));
        if (sql.isEmpty()) return null;
        var ret = jdbcTemplate.query(String.format("SELECT * FROM vouchers where wallet_id in (%s)", sql)
                , voucherRowMapper
                , wallets.toArray());
        return ret;
    }

    public RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var amount = resultSet.getLong("amount");
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return VoucherGenerator.createVoucher(voucherId, amount, voucherType, createdAt);
    };
}
