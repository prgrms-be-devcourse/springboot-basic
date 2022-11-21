package org.prgrms.voucherapplication.customer.repository;

import org.prgrms.voucherapplication.customer.dto.ResponseBlacklist;

import java.util.List;

public interface BlackListRepository {

    List<ResponseBlacklist> findAll();
}
