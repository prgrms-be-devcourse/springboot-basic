package org.devcourse.springbasic.domain.customer.service;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.customer.dao.CustomerRepository;
import org.devcourse.springbasic.domain.customer.dto.CustomerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public UUID save(CustomerDto.SaveRequestDto customerDto) {
        return customerRepository.save(customerDto);
    }
}
