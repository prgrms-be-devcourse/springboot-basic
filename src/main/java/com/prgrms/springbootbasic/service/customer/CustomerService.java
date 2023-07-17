package com.prgrms.springbootbasic.service.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import com.prgrms.springbootbasic.dto.customer.request.CustomerCreateRequest;
import com.prgrms.springbootbasic.dto.customer.request.CustomerUpdateRequest;
import com.prgrms.springbootbasic.dto.customer.response.CustomerListResponse;
import com.prgrms.springbootbasic.dto.customer.response.CustomerResponse;
import com.prgrms.springbootbasic.repository.customer.CustomerRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    //생성(create)
    public CustomerResponse createCustomer(CustomerCreateRequest customerCreateRequest) {
        String name = customerCreateRequest.getName();
        String email = customerCreateRequest.getEmail();
        LocalDateTime createAt = customerCreateRequest.getCreateAt() == null ? LocalDateTime.now() : customerCreateRequest.getCreateAt();

        Customer customer = new Customer(UUID.randomUUID(), name, email, createAt);
        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerResponse(savedCustomer.getCustomerId(), savedCustomer.getName(), savedCustomer.getEmail(), savedCustomer.getCreateAt());
    }

    //조회(Read) - id를 통해서 조회
    public CustomerResponse findById(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("조회하시는 고객은 존재하지 않습니다."));

        return new CustomerResponse(customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getCreateAt());
    }


    //조회(read - custmer의 모든 List를 조회하기)
    public CustomerListResponse findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponseList = customers.stream()
                .map(customer -> new CustomerResponse(
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getCreateAt()))
                .collect(Collectors.toList());

        return new CustomerListResponse(customerResponseList);
    }

    //조회(Read) - 생성일을 통해서 조회
    public CustomerListResponse findByCreateAt() {
        List<Customer> customers = customerRepository.findByCreatedAt();
        List<CustomerResponse> customerResponseList = customers.stream()
                .map(customer -> new CustomerResponse(customer.getCustomerId(), customer.getName(), customer.getEmail(), customer.getCreateAt()))
                .collect(Collectors.toList());
        return new CustomerListResponse(customerResponseList);
    }

    //수정(Update)
    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest) {
        UUID customerId = customerUpdateRequest.getCustomerId();
        Optional<Customer> storedCustomer = customerRepository.findById(customerId);

        Customer customer = storedCustomer.orElseThrow(() -> new IllegalArgumentException("해당 고객은 존재하지 않습니다."));

        customer.setName(customerUpdateRequest.getCustomerName());
        customer.setEmail(customerUpdateRequest.getCustomerEmail());

        customerRepository.update(customer);
    }

    //삭제(Delete) -id
    public int deleteById(UUID customerId) {
        if (!customerRepository.checkCustomerId(customerId)) {
            throw new NoSuchElementException("삭제하려는 고객의 ID는 존재하지 않습니다. 다시 확인 후, 입력해주세요");
        }
        return customerRepository.deleteById(customerId);
    }

    //삭제(Delete)
    public void deleteAllCustomer() {
        customerRepository.deleteAll();
    }

    public boolean existById(UUID customerId) {
        return customerRepository.checkCustomerId(customerId);
    }
}
