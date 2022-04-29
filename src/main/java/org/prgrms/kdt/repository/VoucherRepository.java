package org.prgrms.kdt.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.domain.Voucher;

public interface VoucherRepository {

  Optional<Voucher> findById(UUID voucherId);

  Optional<Voucher> save(Voucher voucher);

  Optional<Voucher> update(Voucher voucher);

  List<Voucher> findAll();

  void delete(UUID voucherId, UUID customerId);

  void deleteById(UUID voucherId);

  void deleteAll();

  List<Voucher> findByCustomerId(UUID customerId);

  List<Voucher> findByTypeAndCreatedAt(Integer type, LocalDateTime startAt, LocalDateTime endAt);
}