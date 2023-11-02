package org.prgms.springbootbasic.repository.customervouchermanagement;

import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface CustomerVoucherManagementRepository {
    void allocateVoucherById(UUID customerId, UUID voucherId);
    void deleteVoucherById(UUID customerId, UUID voucherId);
    void deleteAll();
    List<Voucher> searchVouchersByCustomerId(UUID customerId);
    List<Customer> searchCustomersByVoucherId(UUID voucherId);
}
