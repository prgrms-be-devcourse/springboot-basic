package com.programmers.springbasic.domain.customer.service;

import com.programmers.springbasic.domain.customer.dto.request.CustomerCreateRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerDeleteRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerSingleFindRequestDTO;
import com.programmers.springbasic.domain.customer.dto.request.CustomerUpdateRequestDTO;
import com.programmers.springbasic.domain.customer.dto.response.CustomerResponseDTO;
import com.programmers.springbasic.domain.customer.entity.Customer;
import com.programmers.springbasic.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public void createCustomer(CustomerCreateRequestDTO customerCreateRequestDTO) {
        String name = customerCreateRequestDTO.getName();
        String email = customerCreateRequestDTO.getEmail();

        Customer customer = new Customer(name, email);
        customerRepository.save(customer);
    }

    public List<CustomerResponseDTO> getAllInfo() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(CustomerResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CustomerResponseDTO findCustomer(CustomerSingleFindRequestDTO customerSingleFindRequestDTO) {
        String validCustomerId = customerSingleFindRequestDTO.getCustomerId();
        UUID customerId = UUID.fromString(validCustomerId);

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("조회하고자 하는 고객이 없습니다."));

        return new CustomerResponseDTO(customer);
    }

    public void updateCustomer(CustomerUpdateRequestDTO customerUpdateRequestDTO) {
        UUID customerId = UUID.fromString(customerUpdateRequestDTO.getCustomerId());
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("조회하고자 하는 고객이 없습니다."));

        String newName = customerUpdateRequestDTO.getName();
        String newEmail = customerUpdateRequestDTO.getEmail();

        customer.changeName(newName);
        customer.changeEmail(newEmail);

        customerRepository.update(customer);
    }

    public void removeCustomer(CustomerDeleteRequestDTO customerDeleteRequestDTO) {
        String validCustomerId = customerDeleteRequestDTO.getCustomerId();
        UUID customerId = UUID.fromString(validCustomerId);

        customerRepository.delete(customerId);
    }

    private String getInfo(Customer customer) {
        StringBuilder sb = new StringBuilder();

        sb.append(CustomerInfo.CUSTOMER_ID_INFO_MESSAGE.getInfoMessage() + customer.getCustomerId() + "\n")
                .append(CustomerInfo.CUSTOMER_NAME_INFO_MESSAGE.getInfoMessage() + customer.getName() + "\n")
                .append(CustomerInfo.CUSTOMER_EMAIL_INFO_MESSAGE.getInfoMessage() + customer.getEmail() + "\n");

        return sb.toString();
    }
}
