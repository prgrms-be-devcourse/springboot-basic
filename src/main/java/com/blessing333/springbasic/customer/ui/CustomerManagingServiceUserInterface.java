package com.blessing333.springbasic.customer.ui;

import com.blessing333.springbasic.common.ui.ServiceUserInterface;
import com.blessing333.springbasic.customer.domain.Customer;

import java.util.List;

public interface CustomerManagingServiceUserInterface extends ServiceUserInterface {
    void printCustomerCreateSuccessMessage(Customer customer);
    void printCustomerInformation(Customer customer);
    void printAllCustomerInformation(List<Customer> customerList);
    String requestCustomerId();
    CustomerCreateForm requestCustomerInformation();
}
