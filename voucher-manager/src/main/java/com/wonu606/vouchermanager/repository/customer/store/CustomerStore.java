package com.wonu606.vouchermanager.repository.customer.store;

import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;

public interface CustomerStore {

    CustomerCreateResultSet insert(CustomerCreateQuery query);

    void deleteByCustomerId(String email);
}
