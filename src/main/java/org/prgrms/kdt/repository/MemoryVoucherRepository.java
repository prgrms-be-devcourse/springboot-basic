package org.prgrms.kdt.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.kdt.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("prod")
public class MemoryVoucherRepository implements VoucherRepository {

  private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

  @Override
  public Voucher save(Voucher voucher) {
    storage.put(voucher.getVoucherId(), voucher);
    return voucher;
  }

  @Override
  public List<Voucher> findAll() {
    return List.copyOf(storage.values());
  }
}