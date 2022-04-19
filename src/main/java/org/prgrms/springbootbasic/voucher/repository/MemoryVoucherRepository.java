package org.prgrms.springbootbasic.voucher.repository;

import org.prgrms.springbootbasic.voucher.voucher.Voucher;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {
  private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    return Optional.ofNullable(storage.get(voucherId));
  }

  @Override
  public Voucher insert(Voucher voucher) {
    storage.put(voucher.getVoucherID(), voucher);
    return voucher;
  }

  @Override
  public Map<UUID, Voucher> findAll() {
    return storage;
  }
}
