package org.prgrms.kdtspringdemo.domain.customer.repository;

import org.prgrms.kdtspringdemo.domain.customer.model.BlackCustomer;

import java.util.List;

public interface BlackListRepository {
    List<BlackCustomer> findAllBlackCustomers();
}
