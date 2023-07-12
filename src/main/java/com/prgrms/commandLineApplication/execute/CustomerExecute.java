package com.prgrms.commandLineApplication.execute;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerExecute implements Execute {

  private final Console console;
  private final CustomerService customerService;

  public CustomerExecute(Console console, CustomerService customerService) {
    this.console = console;

    this.customerService = customerService;
  }

  @Override
  public void printList() {
    List<Customer> list = customerService.findAllCustomers();
    console.printAllCustomers(list);
  }

  @Override
  public void create() {
    console.requestCustomerName();
    String customerName = console.readCustomer();

    console.requestCustomerEmail();
    String email = console.readCustomer();

    customerService.create(customerName, email);
    console.printCreateCustomerSuccess(customerName, email);
  }

}
