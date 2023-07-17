package com.prgrms.repository;

import com.prgrms.model.customer.Customer;
import com.prgrms.model.customer.Name;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherCreator;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.DiscountCreator;
import com.prgrms.model.wallet.Wallet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataRowMapper {

    private final DiscountCreator discountCreator;
    private final VoucherCreator voucherCreator;

    public DataRowMapper(DiscountCreator discountCreator, VoucherCreator voucherCreator) {
        this.discountCreator = discountCreator;
        this.voucherCreator = voucherCreator;
    }

    public RowMapper<Wallet> getWalletRowMapper() {
        return (resultSet, i) -> {
            int voucherId = resultSet.getInt("voucher_id");
            int walletId = resultSet.getInt("wallet_id");
            int customerId = resultSet.getInt("customer_id");
            return new Wallet(walletId, customerId, voucherId);
        };
    }

    public RowMapper<Customer> getCustomerRowMapper() {
        return (resultSet, i) -> {
            int customerId = resultSet.getInt("customer_id");
            Name customerName = new Name(resultSet.getString("name"));
            String email = resultSet.getString("email");
            LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                    resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
        };
    }

    public RowMapper<Voucher> getVoucherRowMapper() {
        return (resultSet, i) -> {
            int voucherId = resultSet.getInt("voucher_id");
            VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
            double discountValue = resultSet.getDouble("discount");
            Discount discount = discountCreator.createDiscount(voucherType, discountValue);
            return voucherCreator.createVoucher(voucherId, voucherType, discount);
        };
    }

}
