package com.mountain.voucherApp.application.port.out;


import com.mountain.voucherApp.application.port.in.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerPort {
    CustomerDto insert(CustomerDto customerDtoEntity);
    CustomerDto update(CustomerDto customerDtoEntity);
    void updateVoucherId(UUID customerId, UUID voucherId);
    List<CustomerDto> findAll();
    Optional<CustomerDto> findById(UUID customerId);
    Optional<CustomerDto> findByName(String name);
    Optional<CustomerDto> findByEmail(String email);
    List<CustomerDto> findByVoucherId(UUID voucherId);
    List<CustomerDto> findByVoucherIdNotNull();
    void removeByCustomerId(UUID customerId);
    void removeVoucherId(UUID voucherId);
    int count();
    void deleteAll();
}
