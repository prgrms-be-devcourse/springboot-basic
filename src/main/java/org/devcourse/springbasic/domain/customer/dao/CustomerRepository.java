package org.devcourse.springbasic.domain.customer.dao;

import org.devcourse.springbasic.domain.customer.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {

    UUID save(CustomerDto.SaveRequestDto customer);
    void lastLoginUpdate(CustomerDto.LoginRequestDto customer);
    UUID update(CustomerDto.UpdateRequestDto customer);
    List<CustomerDto.ResponseDto> findAll();
    CustomerDto.ResponseDto findById(UUID customerId);
    CustomerDto.ResponseDto findByName(String name);
    CustomerDto.ResponseDto findByEmail(String email);
}
