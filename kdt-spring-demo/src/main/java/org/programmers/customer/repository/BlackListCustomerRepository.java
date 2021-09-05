package org.programmers.customer.repository;

import org.programmers.customer.model.BlackListCustomer;

import java.io.IOException;
import java.util.List;

public interface BlackListCustomerRepository {
    List<BlackListCustomer> findAllBlackList() throws IOException;

    void save(BlackListCustomer blackListCustomer);
}
