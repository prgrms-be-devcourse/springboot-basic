package org.prgrms.springorder.domain.customer.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.springorder.domain.customer.Wallet;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.repository.CustomerRepository;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("dev")
public class CustomerService {

    private final CustomerRepository customerRepository; // 메모리나 파일에서도.

    private final VoucherService voucherService;

    private final BlockCustomerService blockCustomerService;

    public CustomerService(
        CustomerRepository customerRepository,
        VoucherService voucherService,
        BlockCustomerService blockCustomerService) {
        this.customerRepository = customerRepository;
        this.voucherService = voucherService;
        this.blockCustomerService = blockCustomerService;
    }

    @Transactional(readOnly = true)
    public Wallet findAllVouchers(UUID customerId) {
        return customerRepository.findByIdWithVouchers(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Wallet.class, customerId));
    }

    @Transactional
    public void deleteVoucher(UUID customerId, UUID voucherId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Customer.class, customerId));

        voucherService.deleteVoucherByCustomerId(voucherId, customer.getCustomerId());
    }

     @Transactional
    public void allocateVoucher(UUID customerId, UUID voucherId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Customer.class, customerId));

        voucherService.changeCustomerId(voucherId, customer.getCustomerId());
    }

    @Transactional(readOnly = true)
    public List<BlockCustomer> findAllBlockCustomers() {
        return blockCustomerService.findAll();
    }

    @Transactional
    public Customer createCustomer(String email, String name) {
        Customer customer = new Customer(UUID.randomUUID(), name, email);
        return customerRepository.insert(customer);
    }

}