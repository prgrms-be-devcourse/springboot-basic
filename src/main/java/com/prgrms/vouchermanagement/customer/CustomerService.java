package com.prgrms.vouchermanagement.customer;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void addCustomer(String name, String email) throws DataAccessException, IllegalArgumentException {
        if (isRegisteredCustomer(email)) {
            throw new IllegalArgumentException("중복된 email입니다.");
        }

        Customer newCustomer = Customer.of(UUID.randomUUID(), name, email, LocalDateTime.now());

        customerRepository.save(newCustomer);
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
