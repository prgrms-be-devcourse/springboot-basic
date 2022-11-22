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

    // 특정 고객에게 바우처를 할당 -> 바우처를 만들어서 할당 -> 고객 서비스 // post /api/customers/1/vouchers
    // or post /api/vouchers/1/customers
    @Transactional
    public UUID addVoucher(UUID customerId, VoucherType voucherType, long discountAmount) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Customer.class, customerId));

        return voucherService.createVoucher(voucherType,
                discountAmount,
                customer.getCustomerId())
            .getVoucherId();
    }

    // 고객이 어떤 바우처를 보유하고 있는지 조회 -> 고객역할 // get /api/customers/1/vouchers
    @Transactional(readOnly = true)
    public Wallet findAllVouchers(UUID customerId) {
        return customerRepository.findByIdWithVouchers(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Wallet.class, customerId));
    }

    // 고객이 보유한 바우처 제거 ->  -> delete /api/customers/1/vouchers/2
    @Transactional
    public void deleteVoucher(UUID customerId, UUID voucherId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new EntityNotFoundException(Customer.class, customerId));

        voucherService.deleteVoucherByCustomerId(voucherId, customer.getCustomerId());
    }

    // 특정 고객에게 바우처를 할당
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

    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

}