package team.marco.voucher_management_system.repository;

import java.util.List;
import java.util.UUID;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.model.Voucher;

public interface VoucherCustomerFacade {
    boolean hasVoucher(String voucherId);

    boolean hasCustomer(String customerId);

    List<Voucher> getVouchers(List<UUID> voucherIds);

    List<Customer> getCustomers(List<UUID> customerIds);
}
