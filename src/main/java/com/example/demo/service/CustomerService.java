package com.example.demo.service;

import com.example.demo.domain.customer.Customer;
import com.example.demo.dto.CustomerDto;
import com.example.demo.repository.customer.CustomerRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDto save(String name, int age) {
        Customer customer = new Customer(name, age);
        customerRepository.save(customer);
        return CustomerDto.from(customer);
    }

    public CustomerDto findById(UUID id) {
        return CustomerDto.from(customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id에 해당하는 customer가 없습니다.")));
    }

    public List<CustomerDto> readList() {
        List<Customer> customerList = customerRepository.findAll();

        return customerList.stream()
                .map(CustomerDto::from)
                .toList();
    }

    public void updateName(UUID id, String name) {
        if (customerRepository.notExistById(id)) {
            throw new IllegalArgumentException(String.format("고객 name 업데이트 오류 : 입력 된 id에 해당하는 고객이 존재하지 않습니다. 입력 받은 id : %s ", id.toString()));
        }

        customerRepository.updateName(id, name);
    }

    public void deleteById(UUID id) {
        if (customerRepository.notExistById(id)) {
            throw new IllegalArgumentException(String.format("고객 삭제 오류 : 입력 된 id에 해당하는 고객이 존재하지 않습니다. 입력 받은 id : %s ", id.toString()));
        }

        customerRepository.deleteById(id);
    }
}
