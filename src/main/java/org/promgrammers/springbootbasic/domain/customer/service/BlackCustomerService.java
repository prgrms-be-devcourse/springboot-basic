package org.promgrammers.springbootbasic.domain.customer.service;

import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomerResponse;
import org.promgrammers.springbootbasic.domain.customer.dto.response.CustomersResponse;
import org.promgrammers.springbootbasic.domain.customer.model.Customer;
import org.promgrammers.springbootbasic.domain.customer.model.CustomerType;
import org.promgrammers.springbootbasic.domain.customer.repository.impl.BlackCustomerRepository;
import org.promgrammers.springbootbasic.exception.EmptyListException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlackCustomerService {

    private final BlackCustomerRepository blackCustomerRepository;

    public BlackCustomerService(BlackCustomerRepository blackCustomerRepository) {
        this.blackCustomerRepository = blackCustomerRepository;
    }

    public CustomersResponse findAllByCustomerType(CustomerType customerType) {
        List<Customer> customers = blackCustomerRepository.findAllByCustomerType(customerType);

        if (customers == null || customers.isEmpty()) {
            throw new EmptyListException("해당 타입으로 저장된 고객이 없습니다."); // 예외를 던지는 처리
        }

        return new CustomersResponse(customers.stream()
                .map(customer -> new CustomerResponse(customer.getCustomerId(), customer.getUsername(), customer.getCustomerType()))
                .collect(Collectors.toList()));
    }
}
