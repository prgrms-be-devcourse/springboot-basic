package com.wonu606.vouchermanager.repository.customer;

import com.wonu606.vouchermanager.repository.customer.query.CustomerCreateQuery;
import com.wonu606.vouchermanager.repository.customer.reader.CustomerReader;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerCreateResultSet;
import com.wonu606.vouchermanager.repository.customer.resultset.CustomerResultSet;
import com.wonu606.vouchermanager.repository.customer.store.CustomerStore;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CustomerJdbcRepository implements CustomerRepository {

    CustomerReader reader;
    CustomerStore store;

    public CustomerJdbcRepository(CustomerReader reader, CustomerStore store) {
        this.reader = reader;
        this.store = store;
    }

    @Override
    public List<CustomerResultSet> findAll() {
        return reader.findAll();
    }

    @Override
    public CustomerCreateResultSet insert(CustomerCreateQuery query) {
        return store.insert(query);
    }

    @Override
    public void deleteByCustomerId(String email) {
        store.deleteByCustomerId(email);
    }
}
