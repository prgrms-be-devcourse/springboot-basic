package org.prgms.voucheradmin.domain.customer.dao.blacklist;

import java.util.List;

import org.prgms.voucheradmin.domain.customer.entity.BlackListCustomer;

public interface BlackListRepository {
    List<BlackListCustomer> getAll();
}
