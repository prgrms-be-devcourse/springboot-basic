package org.prgms.springbootbasic.repository.customer;

import org.prgms.springbootbasic.domain.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile({"local", "test"})
public class CustomerMemoryRepository implements CustomerRepository {
    @Override
    public List<Customer> findBlackAll() {
        return new ArrayList<>();
    }
}
