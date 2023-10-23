package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.customer.CustomerRequestDto;
import org.prgrms.vouchermanager.repository.customer.CsvCustomerRepository;
import org.prgrms.vouchermanager.repository.customer.CustomerRepositroy;
import org.prgrms.vouchermanager.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service = new CustomerService(new CsvCustomerRepository());

    public List<Customer> list() {
        return service.findAll();
    }

    public Customer create(CustomerRequestDto requestDto){
        return service.createCustomer(requestDto);
    }
}
