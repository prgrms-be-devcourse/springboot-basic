package com.prgms.kdtspringorder.domain.model.customer;

import java.util.Map;
import java.util.UUID;

public interface CustomerRepository {
    Map<UUID, Customer> findAll();
}
