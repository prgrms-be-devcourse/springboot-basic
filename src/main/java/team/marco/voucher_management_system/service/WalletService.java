package team.marco.voucher_management_system.service;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.repository.CustomerRepository;
import team.marco.voucher_management_system.repository.VoucherRepository;

@Service
public class WalletService {
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public WalletService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public boolean hasCustomer(String customerId) {
        return false;
    }

    public boolean hasVoucher(String customerId) {
        return false;
    }

    public void supplyVoucher(String customerId, String voucherId) {

    }

    public List<Voucher> findVouchersByCustomerId(String customerId) {
        return Collections.emptyList();
    }

    public void returnVoucher(String customerId, String voucherId) {

    }

    public List<Customer> findCustomersByVoucherId(String voucherId) {
        return Collections.emptyList();
    }
}
