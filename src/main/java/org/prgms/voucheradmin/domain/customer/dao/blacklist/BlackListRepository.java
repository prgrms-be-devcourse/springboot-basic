package org.prgms.voucheradmin.domain.customer.dao.blacklist;

import java.io.IOException;
import java.util.List;

import org.prgms.voucheradmin.domain.customer.entity.Customer;

public interface BlackListRepository {
    List<Customer> getAll() throws IOException;
}
