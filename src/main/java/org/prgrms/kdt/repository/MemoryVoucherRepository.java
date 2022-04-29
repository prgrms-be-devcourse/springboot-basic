package org.prgrms.kdt.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.kdt.domain.Voucher;

public class MemoryVoucherRepository implements VoucherRepository {

  private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    return Optional.empty();
  }

  @Override
  public Optional<Voucher> save(Voucher voucher) {
    storage.put(voucher.getVoucherId(), voucher);
    return Optional.of(voucher);
  }

  @Override
  public Optional<Voucher> update(Voucher voucher) {
    return Optional.ofNullable(storage.put(voucher.getVoucherId(), voucher));
  }

  @Override
  public List<Voucher> findAll() {
    return List.copyOf(storage.values());
  }

  @Override
  public void delete(UUID voucherId, UUID customerId) {
    storage.remove(voucherId);
  }

  @Override
  public void deleteById(UUID voucherId) {
    storage.remove(voucherId);
  }

  @Override
  public void deleteAll() {
    storage.clear();
  }

  @Override
  public List<Voucher> findByCustomerId(UUID customerId) {
    return storage.values().stream().filter(v -> v.getCustomerId() == customerId).toList();
  }

  @Override
  public List<Voucher> findByTypeAndCreatedAt(Integer type, LocalDateTime startAt,
      LocalDateTime endAt) {
    return storage.values().stream()
        .filter(voucher -> voucher.getType().getCode() == type)
        .filter(voucher -> voucher.getCreatedAt().isAfter(startAt))
        .filter(voucher -> voucher.getCreatedAt().isBefore(endAt)).toList();
  }
}