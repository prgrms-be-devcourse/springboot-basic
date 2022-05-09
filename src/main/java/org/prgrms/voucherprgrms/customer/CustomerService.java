package org.prgrms.voucherprgrms.customer;

import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.customer.model.CustomerDTO;
import org.prgrms.voucherprgrms.customer.repository.CustomerRepository;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(@Qualifier("namedParam") CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void createCustomer(CustomerDTO customerDTO){
        if (customerRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
            logger.error("Email 중복 input : {}", customerDTO.getEmail());
            throw new RuntimeException();
        }
        Customer customer = toCustomer(customerDTO);
        customerRepository.insert(customer);
    }

    @Transactional
    public boolean allocateVoucher(Customer customer, Voucher voucher) {
        if (customer.allocateVoucher(voucher)) {
            customerRepository.changeVoucher(customer);
            return true;
        } else {
            logger.error("Can't allocate voucher to customer {}", customer.getCustomerId());
            return false;
        }
    }

    @Transactional
    public void removeVoucher(Customer customer) {
        customer.removeVoucher();
        customerRepository.changeVoucher(customer);
    }

    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    /**
     * DTO to Customer
     *
     * @param customerDTO
     * @return customer
     */
    private Customer toCustomer(CustomerDTO customerDTO) {
        UUID customerId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Customer customer = new Customer(customerId, customerDTO.getName(), customerDTO.getEmail(), createdAt);
        return customer;
    }


}
