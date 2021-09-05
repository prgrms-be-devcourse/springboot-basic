package org.prgms.w3d1.model.customer;

import org.prgms.w3d1.repository.CustomerRepository;
import org.prgms.w3d1.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public CustomerService(CustomerRepository customerRepository, VoucherRepository voucherRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    public Customer getCustomer(UUID customerId){
        return customerRepository
            .findById(customerId)
            .orElseThrow(()-> new RuntimeException("Can not find a customer for " + customerId));
    }

    public void saveCustomer(Customer customer){
        customerRepository.insert(customer);
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        var voucher = voucherRepository.findById(voucherId);

        if(voucher.isEmpty()) {
            throw new RuntimeException("There is no Voucher :" + voucherId.toString());
        }

        return customerRepository.findById(voucher.get().getCustomerId());
    }
}
