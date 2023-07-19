package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import java.util.List;

public interface CustomerRepository {

    CustomerCreateResultSet insert(CustomerCreateQuery query);

    List<CustomerResultSet> findAll();

    void deleteByCustomerId(String email);
}
