package com.tangerine.voucher_system.application.customer.service.mapper;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.service.CustomerService;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerParam;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerServiceMapper {

    Customer paramToDomain(CustomerParam param);

    CustomerResult domainToParam(Customer domain);
    
    List<CustomerResult> domainsToResults(List<Customer> domains);
    
}
