package kr.co.programmers.springbootbasic.wallet.repository.impl;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.domain.impl.JdbcCustomer;
import kr.co.programmers.springbootbasic.wallet.domain.Wallet;
import kr.co.programmers.springbootbasic.wallet.repository.WalletRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("deploy")
public class JdbcWalletRepository implements WalletRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveVoucherInCustomerWallet(UUID walletId, UUID voucherId) {
        jdbcTemplate.update("INSERT INTO wallet (id, voucher_id) VALUES (UUID_TO_BIN(?), UUID_TO_BIN(?))",
                walletId.toString().getBytes(),
                voucherId.toString().getBytes());
    }

    @Override
    public List<Wallet> findAllVouchersById(UUID walletId) {
        return jdbcTemplate.query("SELECT * FROM voucher WHERE wallet_id = UUID_TO_BIN(?)",
                walletRowMapper(),
                walletId.toString().getBytes());
    }

    @Override
    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM wallet AS w JOIN customer AS c ON w.id = c.wallet_id WHERE w.voucher_id = UUID_TO_BIN(?)",
                    customerRowMapper(),
                    voucherId.toString().getBytes()
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteByVoucherId(UUID voucherId) {
        jdbcTemplate.update("DELETE FROM voucher WHERE id = UUID_TO_BIN(?)",
                voucherId.toString().getBytes());
    }

    @Override
    public void handOverVoucherToCustomer(UUID voucherId, UUID walletId) {
        jdbcTemplate.update("UPDATE wallet SET id = UUID_TO_BIN(?) WHERE voucher_id = UUID_TO_BIN(?)",
                walletId.toString().getBytes(),
                voucherId.toString().getBytes());
    }

    private RowMapper<Wallet> walletRowMapper() {
        return (rs, rowNum) -> {
            var walletId = toUUID(rs.getBytes("id"));
            var voucherId = toUUID(rs.getBytes("voucher_id"));

            return new Wallet(walletId, voucherId);
        };
    }

    private RowMapper<Customer> customerRowMapper() {
        return (rs, rowNum) -> {
            var id = toUUID(rs.getBytes("c.id"));
            var name = rs.getString("name");
            var statusId = CustomerStatus.resolveId(rs.getInt("status_id"));
            var walletId = toUUID(rs.getBytes("wallet_id"));

            return new JdbcCustomer(id, name, statusId, walletId);
        };
    }

    private UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
