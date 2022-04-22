package org.prgms.kdtspringvoucher.wallet;

import org.prgms.kdtspringvoucher.customer.domain.Customer;
import org.prgms.kdtspringvoucher.customer.repository.CustomerRepository;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Voucher> showVouchersAssignedToCustomer(Customer customer){
        List<Voucher> vouchers = voucherRepository.findByCustomerId(customer.getCustomerId());
        vouchers.forEach(System.out::println);
        return vouchers;
    }

    public void deleteVoucherAssignedToCustomer(Customer customer) {
        voucherRepository.deleteByCustomerId(customer.getCustomerId());
    }

    public Customer showCustomerByVoucherId(Voucher voucher){
        Optional<Customer> customer = customerRepository.findById(voucher.getCustomerId());
        if (customer.isPresent()) {
            System.out.println(customer.get());
            return customer.get();
        }

        System.out.println("The voucher didn't assign to customer");
        return null;
    }
}
