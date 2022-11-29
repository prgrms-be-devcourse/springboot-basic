package org.prgrms.voucherapplication.domain.customer.repository;

import org.prgrms.voucherapplication.domain.customer.controller.dto.ResponseBlacklist;

import java.util.List;

public interface BlackListRepository {

    List<ResponseBlacklist> findAll();
}
