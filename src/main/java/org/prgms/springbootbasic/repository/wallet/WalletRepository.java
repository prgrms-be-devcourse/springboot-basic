package org.prgms.springbootbasic.repository.wallet;

import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    void allocateVoucherById(UUID customerId, UUID voucherId);
    void deleteVoucherById(UUID customerId, UUID voucherId);
    List<VoucherPolicy> searchVouchersByCustomerId(UUID customerId);
    List<Customer> searchCustomersByVoucherId(UUID voucherId);
}
