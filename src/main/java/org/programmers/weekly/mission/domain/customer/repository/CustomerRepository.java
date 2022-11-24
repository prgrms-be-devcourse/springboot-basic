package org.programmers.weekly.mission.domain.customer.repository;

import org.programmers.weekly.mission.domain.customer.model.BlackCustomer;

import java.util.List;

public interface CustomerRepository {
    List<BlackCustomer> getBlackList();
}
