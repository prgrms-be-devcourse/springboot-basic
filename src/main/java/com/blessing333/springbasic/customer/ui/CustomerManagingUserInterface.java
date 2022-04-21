package com.blessing333.springbasic.customer.ui;

import com.blessing333.springbasic.common.ui.UserInterface;
import com.blessing333.springbasic.customer.domain.Customer;

import java.util.List;

public interface CustomerManagingUserInterface extends UserInterface {
    void printCustomerCreateSuccessMessage(Customer customer);
    void printCustomerInformation(Customer customer);
    void printAllCustomerInformation(List<Customer> customerList);
    String requestCustomerId();
    CustomerCreateForm requestCustomerInformation();
}
