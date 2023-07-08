package com.prgrms.springbootbasic.service.customer;

import com.prgrms.springbootbasic.dto.customer.CustomerDto;
import com.prgrms.springbootbasic.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    //생성(update)
    public CustomerDto create(CustomerDto create) {
        return null;
    }

    //조회(read - custmer의 모든 List를 조회하기)
    public CustomerDto findAllCustomer() {
        return null;
    }

    //수정(update)
    public CustomerDto update(CustomerDto update) {
        return null;
    }

    //삭제
    public void delete(String id) {
    }
}
