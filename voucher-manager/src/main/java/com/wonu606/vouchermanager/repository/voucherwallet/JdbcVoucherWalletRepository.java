package com.wonu606.vouchermanager.repository.voucherwallet;

import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWallet;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import java.util.List;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcVoucherWalletRepository implements VoucherWalletRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherWalletRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<UUID> findOwnedVouchersByCustomer(Email email) {
        String selectSql = "SELECT voucher_id FROM customer_voucher_wallet WHERE customer_email_address = ?";
        return jdbcTemplate.query(selectSql,
                (rs, rowNum) -> UUID.fromString(rs.getString("voucher_id")),
                email.getAddress());
    }

    @Override
    public void delete(VoucherWallet wallet) {
        String deleteSql = "DELETE FROM customer_voucher_wallet WHERE customer_email_address = ? AND voucher_id = ?";
        jdbcTemplate.update(deleteSql,
                wallet.getEmailAddress(),
                wallet.getVoucherId().toString());
    }

    @Override
    public VoucherWallet save(VoucherWallet wallet) {
        insert(wallet);
        return wallet;
    }

    @Override
    public List<String> findOwnedCustomerByVoucher(UUID voucherId) {
        String selectSql = "SELECT customer_email_address FROM customer_voucher_wallet WHERE voucher_id = ?";
        return jdbcTemplate.query(selectSql,
                (rs, rowNum) -> rs.getString("customer_email_address"),
                voucherId.toString());
    }

    private void insert(VoucherWallet wallet) {
        String insertSql = "INSERT INTO customer_voucher_wallet (customer_email_address, voucher_id) VALUES (?, ?)";
        jdbcTemplate.update(insertSql,
                wallet.getEmailAddress(),
                wallet.getVoucherId().toString());
    }
}
