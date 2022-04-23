package org.prgrms.springbootbasic.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.springbootbasic.controller.VoucherType;
import org.prgrms.springbootbasic.entity.customer.Customer;
import org.prgrms.springbootbasic.exception.DuplicateCustomerEmailException;
import org.prgrms.springbootbasic.exception.InvalidCustomerIdException;
import org.prgrms.springbootbasic.exception.InvalidVoucherIdException;
import org.prgrms.springbootbasic.repository.customer.CustomerRepository;
import org.prgrms.springbootbasic.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;

    public CustomerService(CustomerRepository customerRepository,
        VoucherRepository voucherRepository) {
        this.customerRepository = customerRepository;
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public UUID createCustomer(String name, String email) {
        logger.info("createCustomer() called");

        validateDuplicateEmail(email);

        return customerRepository.save(new Customer(UUID.randomUUID(), name, email));
    }

    public List<Customer> findAllCustomers() {
        logger.info("findAllCustomers() called");

        return customerRepository.findAll();
    }

    @Transactional
    public void deleteVoucher(UUID customerId, UUID voucherId) {
        logger.info("deleteVoucher() called");

        var customer = customerRepository.findById(customerId)
            .orElseThrow(InvalidCustomerIdException::new);

        var customerVouchers = voucherRepository.findByCustomer(customer);

        var deletedVoucher = customerVouchers.stream()
            .filter(voucher -> voucher.getVoucherId().equals(voucherId))
            .findFirst()
            .orElseThrow(InvalidVoucherIdException::new);

        voucherRepository.deleteVoucher(deletedVoucher);
    }

    public List<Customer> findCustomerHavingSpecificVoucherType(VoucherType voucherType) {
        return customerRepository.findByVoucherType(voucherType);
    }

    private void validateDuplicateEmail(String email) {
        logger.info("validateDuplicateEmail() called");

        var customers = customerRepository.findByEmail(email);
        if (customers.isPresent()) {
            throw new DuplicateCustomerEmailException();
        }
    }
}
