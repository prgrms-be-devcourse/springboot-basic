package org.prgrms.voucherapplication.customer;

import org.prgrms.voucherapplication.dto.ResponseBlacklist;

import java.util.List;

public interface CustomerService {

    void createCustomers(List<Customer> customers);

    List<ResponseBlacklist> findBlacklist();
}
