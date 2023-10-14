package org.prgrms.vouchermanager.Repository;

import org.prgrms.vouchermanager.domain.customer.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerRepositroy {
    List<Customer> findAll();
}
