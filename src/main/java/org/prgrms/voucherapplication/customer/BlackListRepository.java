package org.prgrms.voucherapplication.customer;

import org.prgrms.voucherapplication.dto.ResponseBlacklist;

import java.util.List;

public interface BlackListRepository {

    List<ResponseBlacklist> findAll();
}
