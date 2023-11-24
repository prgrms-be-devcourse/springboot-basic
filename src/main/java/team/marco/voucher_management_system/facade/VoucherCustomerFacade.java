package team.marco.voucher_management_system.facade;

import java.util.List;
import java.util.UUID;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.model.Voucher;

public interface VoucherCustomerFacade {
    boolean hasVoucher(UUID voucherId);

    boolean hasCustomer(UUID customerId);

    List<Voucher> getVouchers(List<UUID> voucherIds);

    List<Customer> getCustomers(List<UUID> customerIds);
}
