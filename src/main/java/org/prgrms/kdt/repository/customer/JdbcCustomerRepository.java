package org.prgrms.kdt.repository.customer;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.model.customer.CustomerGrade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Qualifier("jdbc")
@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public List<Customer> findAllByCustomerGrade(CustomerGrade customerGrade) {
        return null;
    }
}
