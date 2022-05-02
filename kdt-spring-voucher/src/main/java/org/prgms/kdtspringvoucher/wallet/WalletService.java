package org.prgms.kdtspringvoucher.wallet;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.repository.CustomerRepository;
import org.prgms.kdtspringvoucher.customer.service.CustomerService;
import org.prgms.kdtspringvoucher.voucher.domain.FixedAmountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.dto.AssignVoucherRequest;
import org.prgms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.prgms.kdtspringvoucher.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final CustomerService customerService;
    private final VoucherService voucherService;

    public WalletService(CustomerService customerService, VoucherService voucherService) {
        this.customerService = customerService;
        this.voucherService = voucherService;
    }

    //commandLine Application
    public Voucher assignVoucherToCustomer(Customer customer, Voucher voucher) {
        voucher.assignVoucherToCustomer(customer.getCustomerId());
        return voucherService.updateVoucher(voucher);
    }

    public void showVouchersAssignedToCustomer(Customer customer){
        voucherService.getVouchersByCustomerId(customer.getCustomerId()).forEach(System.out::println);
    }

    public void deleteVoucherAssignedToCustomer(UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
    }
    public void showCustomerByVoucherId(Voucher voucher){
        System.out.println(customerService.getCustomerById(voucher.getCustomerId()));
    }

    //MVC
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    public List<Voucher> getVouchersByCustomerId(UUID customerId) {
        return voucherService.getVouchersByCustomerId(customerId);
    }

    public List<Voucher> getVoucherToAssign() {
        return voucherService.getVouchers();
    }

    public Voucher assignVoucherToCustomer(UUID customerId, UUID voucherId) {
        Voucher voucher = voucherService.getVoucherById(voucherId).orElse(null);
        voucher.assignVoucherToCustomer(customerId);
        return voucherService.updateVoucher(voucher);
    }
}
