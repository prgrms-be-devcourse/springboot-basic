package com.mountain.voucherApp.dao.customer;

import com.mountain.voucherApp.dto.CustomerDto;
import com.mountain.voucherApp.model.CustomerEntity;
import com.mountain.voucherApp.model.vo.CustomerName;
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
