package org.prgms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer insert(String name, String email) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, LocalDateTime.now());

        return this.customerRepository.upsert(customer);
    }

    public Customer update(UUID customerId, String name, boolean isBlacked) {
        Customer customer = findById(customerId).orElseThrow(EntityNotFoundException::new);
        Customer updatedCustomer = customer.changeInfo(name, isBlacked);

        return customerRepository.upsert(updatedCustomer);
    }

    public Optional<Customer> findById(UUID customerId){
        return customerRepository.findById(customerId);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void deleteById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public void deleteAll(){
        customerRepository.deleteAll();
    }

    public List<Customer> findBlackAll(){
        return customerRepository.findBlackAll();
    }
}
// 전체적으로 피드백 반영한 후에, 반영 생각과 왜 그렇게 반영했는지. PR에. 테스트 코드 보완도 좀 하고.
