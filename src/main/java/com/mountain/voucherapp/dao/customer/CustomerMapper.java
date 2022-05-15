package com.mountain.voucherapp.dao.customer;

import com.mountain.voucherapp.dto.CustomerDto;
import com.mountain.voucherapp.model.CustomerEntity;
import com.mountain.voucherapp.model.vo.CustomerName;
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
