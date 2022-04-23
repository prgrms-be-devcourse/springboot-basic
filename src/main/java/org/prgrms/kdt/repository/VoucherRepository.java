package org.prgrms.kdt.repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.domain.Voucher;

public interface VoucherRepository {

  Optional<Voucher> findById(UUID voucherId);

  Voucher save(Voucher voucher);

  Voucher update(Voucher voucher);

  List<Voucher> findAll();

  default void delete(UUID voucherId, UUID customerId) {
  }

  default void deleteAll() {
  }

  default List<Voucher> findByCustomerId(UUID customerId) {
    return Collections.emptyList();
  }
}