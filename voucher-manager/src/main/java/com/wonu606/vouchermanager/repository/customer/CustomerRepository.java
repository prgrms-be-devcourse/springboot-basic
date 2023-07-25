package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import java.util.List;

public interface CustomerRepository {

    List<CustomerResultSet> findAll();

    CustomerCreateResultSet insert(CustomerCreateQuery query);

    void deleteByCustomerId(String email);
}
