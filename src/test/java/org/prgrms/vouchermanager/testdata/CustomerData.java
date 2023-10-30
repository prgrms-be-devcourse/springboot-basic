package org.prgrms.vouchermanager.testdata;


import org.prgrms.vouchermanager.domain.customer.Customer;
import org.prgrms.vouchermanager.domain.customer.CustomerRequest;

import java.util.UUID;

public class CustomerData {

    public static Customer getCustomer(){
        return new Customer(UUID.randomUUID(), "jun", "123@gmail.com", true);
    }
    public static CustomerRequest getCustomerDto(){
        return new CustomerRequest("injun", "654@", true);}
}
