package org.programmers.devcourse.voucher.engine.voucher.repository;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements
    VoucherRepository {

  private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

  @Override
  public UUID save(Voucher voucher) {
    UUID voucherId = voucher.getVoucherId();
    storage.put(voucherId, voucher);
    return voucherId;
  }

  @Override
  public Optional<Voucher> getVoucher(UUID voucherId) {

    return Optional.ofNullable(storage.get(voucherId));
  }

  @Override
  public Collection<Voucher> getAllVouchers() {
    return Collections.unmodifiableCollection(storage.values());
  }


}
