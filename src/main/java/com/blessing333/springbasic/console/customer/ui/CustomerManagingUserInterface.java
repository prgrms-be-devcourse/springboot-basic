package com.blessing333.springbasic.console.customer.ui;

import com.blessing333.springbasic.console.ui.UserInterface;
import com.blessing333.springbasic.domain.customer.model.Customer;

import java.util.List;

public interface CustomerManagingUserInterface extends UserInterface {
    void printRegisterComplete(Customer customer);
    void printCustomerInformation(Customer customer);
    void printAllCustomerInformation(List<Customer> customerList);
    String requestCustomerId();
    CustomerCreateForm requestCustomerInformation();
}
