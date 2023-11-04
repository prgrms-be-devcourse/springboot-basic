package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.dto.CustomerRequest;
import org.prgrms.vouchermanager.repository.customer.CustomerRepositroy;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepositroy customerRepositroy; //yaml 파일에서 Repository 지정
    public List<CustomerRequest> findAll(){
        List<CustomerRequest> result = new ArrayList<>();
        List<Customer> all = customerRepositroy.findAll();
        return all.stream()
                .map(customer ->
                        new CustomerRequest(customer.getName(),
                        customer.getEmail(),
                        customer.getIsBlack()))
                .collect(toList());
    }
    public CustomerRequest createCustomer(CustomerRequest requestDto){
        UUID customerId = UUID.randomUUID();
        Customer customer = customerRepositroy.save(
                new Customer(customerId, requestDto.name(), requestDto.email(), requestDto.isBlack()));
        return new CustomerRequest(customer.getName(), customer.getEmail(), customer.getIsBlack());
    }
}
