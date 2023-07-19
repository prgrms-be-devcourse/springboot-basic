package com.tangerine.voucher_system.application.customer.service.mapper;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerParam;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerServiceMapper {
    CustomerServiceMapper INSTANCE = Mappers.getMapper(CustomerServiceMapper.class);

    Customer paramToDomain(CustomerParam param);

    CustomerResult domainToParam(Customer domain);
}
