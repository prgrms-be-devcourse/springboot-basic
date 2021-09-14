package org.prgrms.kdt.customer.repository;

import org.prgrms.kdt.customer.domain.BannedCustomer;

import java.io.IOException;
import java.util.List;

public interface CustomerRepository {
    List<BannedCustomer> getBlackListCSV() throws IOException;
}
