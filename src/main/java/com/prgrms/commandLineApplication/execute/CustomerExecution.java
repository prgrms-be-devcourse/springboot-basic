package com.prgrms.commandLineApplication.execute;

import com.prgrms.commandLineApplication.customer.dto.CustomerCreateDto;
import com.prgrms.commandLineApplication.customer.dto.CustomerResponseDto;
import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerExecution implements Execution {

  private final Console console;
  private final CustomerService customerService;

  public CustomerExecution(Console console, CustomerService customerService) {
    this.console = console;
    this.customerService = customerService;
  }

  @Override
  public void printList() {
    List<CustomerResponseDto> list = customerService.findAllCustomers();
    console.printAllCustomers(list);
  }

  @Override
  public void create() {
    console.requestCustomerName();
    String customerName = console.readCustomer();

    console.requestCustomerEmail();
    String email = console.readCustomer();

    CustomerCreateDto requestDto = new CustomerCreateDto(customerName, email);
    customerService.create(requestDto);
    console.printCreateCustomerSuccess(customerName, email);
  }

}
