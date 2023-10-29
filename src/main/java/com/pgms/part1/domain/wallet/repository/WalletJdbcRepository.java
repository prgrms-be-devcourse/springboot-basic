package com.pgms.part1.domain.wallet.repository;

import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.customer.entity.CustomerBuilder;
import com.pgms.part1.domain.voucher.entity.Voucher;
import com.pgms.part1.domain.voucher.entity.VoucherDiscountType;
import com.pgms.part1.domain.wallet.entity.Wallet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WalletJdbcRepository implements WalletRepository{

    private final JdbcTemplate jdbcTemplate;

    public WalletJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Wallet mapWallet(ResultSet resultSet) throws SQLException {
        return new Wallet(resultSet.getLong("id"), resultSet.getLong("voucher_id"), resultSet.getLong("customer_id"));
    }

    private Voucher mapVoucher(ResultSet resultSet) throws SQLException {
        return Voucher.newVocher(resultSet.getLong("id"), resultSet.getInt("discount"), VoucherDiscountType.valueOf(resultSet.getString("discount_type")));
    }

    private Customer mapCustomer(ResultSet resultSet) throws SQLException {
        return new CustomerBuilder().id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .isBlocked(resultSet.getBoolean("is_blocked")).build();
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(Long id) {
        String findVouchersByCustomerSql = "SELECT V.id as id, V.discount as discount, V.discount_type as discount_type FROM WALLETS W JOIN Vouchers V ON W.voucher_id = V.id  WHERE W.customer_id = ?";
        return jdbcTemplate.query(findVouchersByCustomerSql, new Object[]{id}, (resultSet, i) -> mapVoucher(resultSet));
    }

    @Override
    public List<Customer> findCustomersByVoucherId(Long id) {
        String findCustomersByVoucherSql = "SELECT C.id as id, C.name as name, C.email as email, C.is_blocked as is_blocked FROM WALLETS W JOIN Customers C ON W.customer_id = C.id  WHERE W.voucher_id = ?";
        return jdbcTemplate.query(findCustomersByVoucherSql, new Object[]{id}, (resultSet, i) -> mapCustomer(resultSet));
    }

    @Override
    public void addWallet(Wallet wallet) {
        String addWalletSql = "INSERT INTO WALLETS(id, voucher_id, customer_id) values (?, ?, ?)";
        jdbcTemplate.update(addWalletSql, wallet.id(), wallet.voucherId(), wallet.customerId());
    }

    @Override
    public void deleteWallet(Long id) {
        String deleteWalletSql = "DELETE FROM WALLETS WHERE id = ?";
        jdbcTemplate.update(deleteWalletSql, id);
    }
}
