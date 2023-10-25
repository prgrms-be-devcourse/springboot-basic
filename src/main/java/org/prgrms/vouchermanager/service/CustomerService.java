package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.customer.CustomerRequestDto;
import org.prgrms.vouchermanager.repository.customer.CustomerRepositroy;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepositroy customerRepositroy; //yaml 파일에서 Repository 지정
    public List<Customer> findAll(){
        return customerRepositroy.findAll();
    }
    public Customer createCustomer(CustomerRequestDto requestDto){
        UUID customerId = UUID.randomUUID();
        return customerRepositroy.save(new Customer(customerId, requestDto.getName(), requestDto.getEmail(), requestDto.getIsBlack()));
    }
}
