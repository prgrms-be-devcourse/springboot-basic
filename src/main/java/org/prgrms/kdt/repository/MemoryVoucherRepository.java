package org.prgrms.kdt.repository;

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
  public Voucher save(Voucher voucher) {
    storage.put(voucher.getVoucherId(), voucher);
    return voucher;
  }

  @Override
  public Voucher update(Voucher voucher) {
    return null;
  }

  @Override
  public List<Voucher> findAll() {
    return List.copyOf(storage.values());
  }
}