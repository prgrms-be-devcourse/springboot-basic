package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Qualifier("memory")
public class MemoryVoucherRepository implements VoucherRepository {

  private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    return Optional.ofNullable(storage.get(voucherId));
  }

  @Override
  public Voucher insert(Voucher voucher) {
    storage.put(voucher.getVoucherId(), voucher);
    return voucher;
  }

  @Override
  public List<Voucher> findAll() {
    return (List<Voucher>) storage.values();
  }
}
