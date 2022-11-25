package org.prgrms.springorder.domain.customer.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.springorder.domain.voucher_wallet.model.Wallet;
import org.prgrms.springorder.domain.customer.model.BlockCustomer;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.repository.CustomerRepository;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.prgrms.springorder.domain.voucher_wallet.service.VoucherWalletService;
import org.prgrms.springorder.global.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final VoucherWalletService voucherWalletService;

    private final BlockCustomerService blockCustomerService;

    private final VoucherService voucherService;

    public CustomerService(
        CustomerRepository customerRepository,
        VoucherWalletService voucherWalletService,
        BlockCustomerService blockCustomerService,
        VoucherService voucherService) {
        this.customerRepository = customerRepository;
        this.voucherWalletService = voucherWalletService;
        this.blockCustomerService = blockCustomerService;
        this.voucherService = voucherService;
    }

    @Transactional(readOnly = true)
    public Wallet findAllVouchers(UUID customerId) {
        return voucherWalletService.findAllVouchers(customerId);
    }

    @Transactional
    public void deleteVoucher(UUID customerId, UUID voucherId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Customer.class, customerId));

        voucherWalletService.deleteVoucherByCustomerId(voucherId, customer.getCustomerId());
    }

    @Transactional
    public void allocateVoucher(UUID customerId, UUID voucherId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Customer.class, customerId));

        boolean existsVoucher = voucherService.existsVoucher(voucherId);

        if (!existsVoucher) {
            throw new EntityNotFoundException(Voucher.class, voucherId);
        }

        voucherWalletService.allocateVoucher(customer.getCustomerId(), voucherId);
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

    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

}