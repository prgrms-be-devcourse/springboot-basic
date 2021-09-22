package org.prgrms.devcourse.voucher;

import org.prgrms.devcourse.customer.Customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VoucherWalletService {
    VoucherUseInfo issueVoucher(Customer customer, Voucher voucher);
    List<VoucherUseInfo> findCustomerVoucherWallet(Customer customer);
    List<Customer> findCustomerList(Voucher voucher);
    UUID recallVoucher(VoucherUseInfo voucherUseInfo);
    VoucherUseInfo extendVoucherExpirationDate(VoucherUseInfo voucherUseInfo, LocalDateTime expirationDate);
}