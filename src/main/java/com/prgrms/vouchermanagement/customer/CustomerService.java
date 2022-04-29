package com.prgrms.vouchermanagement.customer;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public UUID addCustomer(String name, String email) throws DataAccessException, IllegalArgumentException {
        if (isRegisteredCustomer(email)) {
            throw new IllegalArgumentException("중복된 email입니다.");
        }

        UUID customerId = UUID.randomUUID();
        Customer newCustomer = Customer.of(customerId, name, email, LocalDateTime.now());

        customerRepository.save(newCustomer);

        return customerId;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> findCustomerByVoucher(UUID voucherId) throws DataAccessException {
        return customerRepository.findCustomerByVoucher(voucherId);
    }

    public Optional<Customer> findById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public void removeCustomer(UUID customerId) {
        customerRepository.remove(customerId);
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public boolean isRegisteredCustomer(String email) throws DataAccessException {
        return email != null && customerRepository.findByEmail(email).isPresent();
    }

    /**
     * @throws DataAccessException : Repository에서 쿼리 실행에 문제가 발생한 경우 던져진다.
     */
    public boolean isRegisteredCustomer(UUID customerId) throws DataAccessException {
        return customerId != null && customerRepository.findById(customerId).isPresent();
    }
}
