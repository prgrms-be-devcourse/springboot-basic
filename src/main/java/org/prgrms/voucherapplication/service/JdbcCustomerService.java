package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.repository.customer.jdbc.JdbcCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JdbcCustomerService {

    private JdbcCustomerRepository customerRepository;

    public JdbcCustomerService(JdbcCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * DB에 고객 저장
     * @param customer
     */
    public void saveCustomer(Customer customer) {
        customerRepository.insert(customer);
    }

    /**
     * 모든 고객 리스트 반환
     * @return
     */
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    /**
     * 고객 id로 고객 정보 조회
     * @param customerId
     * @return
     */
    public Optional<Customer> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    /**
     * 고객 이름으로 고객 정보 조회
     * @param name
     * @return
     */
    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    /**
     * 고객 이메일로 고객 정보 조회
     * @param email
     * @return
     */
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
