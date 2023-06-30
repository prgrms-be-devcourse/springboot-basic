package org.prgrms.kdt.repository.customer;

import java.util.List;

public interface CustomerRepository {
    List<List<String>> findAllBlackList();
}
