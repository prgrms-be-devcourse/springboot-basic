package org.prgrms.kdtspringdemo.domain.blacklist.repository;

import org.prgrms.kdtspringdemo.domain.blacklist.model.BlackCustomer;

import java.util.List;

public interface BlackListRepository {
    List<BlackCustomer> findAllBlackCustomers();
}
