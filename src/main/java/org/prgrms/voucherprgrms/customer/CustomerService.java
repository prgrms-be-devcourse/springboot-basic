package org.prgrms.voucherprgrms.customer;

import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.customer.model.CustomerDTO;
import org.prgrms.voucherprgrms.customer.repository.CustomerRepository;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class CustomerService {

    private final static Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    public CustomerService(@Qualifier("named") CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //TODO email 중복 Exception 처리
    public void createCustomer(CustomerDTO customerDTO) throws Exception {
        if (customerRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
            logger.error("Email 중복 input : {}", customerDTO.getEmail());
            throw new Exception();
        }
        Customer customer = toCustomer(customerDTO);
        customerRepository.insert(customer);
    }

    public void allocateVoucher(Customer customer, Voucher voucher) {
        if (customer.allocateVoucher(voucher)) {
            customerRepository.allocateVoucher(customer);
        } else {
            logger.error("Can't allocate voucher to customer {}", customer.getCustomerId());
            //throw
        }

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
