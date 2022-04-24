package com.mountain.voucherApp.adapter.out.persistence.customer;

import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.domain.vo.CustomerName;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    CustomerDto mapToDomainEntity(CustomerEntity customerEntity) {
        if (customerEntity == null)
            return null;
        return new CustomerDto(
                customerEntity.getCustomerId(),
                customerEntity.getVoucherId(),
                new CustomerName(customerEntity.getName()),
                customerEntity.getEmail(),
                customerEntity.getLastLoginAt(),
                customerEntity.getCreatedAt()
        );
    }
}
