package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.CustomerVoucherEntity;
import org.prgrms.kdt.domain.VoucherEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerVoucherRepository {

    CustomerVoucherEntity insert(CustomerVoucherEntity customerVoucherEntity);

    void deleteAll();

    void deleteById(UUID customerId, UUID voucherId);

    List<CustomerVoucherEntity> findAll();

    Optional<CustomerVoucherEntity> findById(UUID customerVoucherId);

    List<UUID> findByCustomerId(UUID customerId);

    Optional<CustomerEntity> findByVoucherId(UUID voucherId);
}


