package com.tangerine.voucher_system.application.customer.service.mapper;

import com.tangerine.voucher_system.application.customer.model.Customer;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerParam;
import com.tangerine.voucher_system.application.customer.service.dto.CustomerResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerServiceMapper {

    public Customer paramToDomain(CustomerParam param) {
        return new Customer(
                param.customerId(),
                param.name(),
                param.isBlack()
        );
    }

    public CustomerResult domainToParam(Customer domain) {
        return new CustomerResult(
                domain.customerId(),
                domain.name(),
                domain.isBlack()
        );
    }

    public List<CustomerResult> domainsToResults(List<Customer> domains) {
        return domains.stream()
                .map(this::domainToParam)
                .toList();
    }

}
