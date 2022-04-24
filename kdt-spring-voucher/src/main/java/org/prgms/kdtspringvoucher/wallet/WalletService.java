package org.prgms.kdtspringvoucher.wallet;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.repository.CustomerRepository;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public WalletService(CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public Voucher assignVoucherToCustomer(Customer customer, Voucher voucher) {
        voucher.assignVoucherToCustomer(customer);
        return voucherRepository.update(voucher);
    }

    public void showVouchersAssignedToCustomer(Customer customer){
        voucherRepository.findByCustomerId(customer.getCustomerId()).forEach(System.out::println);
    }

    public void deleteVoucherAssignedToCustomer(Customer customer) {
        voucherRepository.deleteByCustomerId(customer.getCustomerId());
    }

    public void showCustomerByVoucherId(Voucher voucher){
        System.out.println(customerRepository.findById(voucher.getCustomerId()).orElse(null));
    }
}
