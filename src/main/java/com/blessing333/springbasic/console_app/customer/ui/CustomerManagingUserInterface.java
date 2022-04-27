package com.blessing333.springbasic.console_app.customer.ui;

import com.blessing333.springbasic.console_app.ui.UserInterface;
import com.blessing333.springbasic.customer.domain.Customer;

import java.util.List;

public interface CustomerManagingUserInterface extends UserInterface {
    void printRegisterComplete(Customer customer);
    void printCustomerInformation(Customer customer);
    void printAllCustomerInformation(List<Customer> customerList);
    String requestCustomerId();
    CustomerCreateForm requestCustomerInformation();
}
