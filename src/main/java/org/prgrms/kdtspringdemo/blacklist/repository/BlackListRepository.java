package org.prgrms.kdtspringdemo.blacklist.repository;

import org.prgrms.kdtspringdemo.blacklist.model.BlackCustomer;

import java.util.List;

public interface BlackListRepository {
    List<BlackCustomer> findAllBlackCustomers();
}
