package org.prgrms.kdt.repository;

import java.util.*;
import org.prgrms.kdt.model.Customer;

public interface CustomerRepository {

    Map<UUID, Customer> findAllBlackListCustomer();

}
