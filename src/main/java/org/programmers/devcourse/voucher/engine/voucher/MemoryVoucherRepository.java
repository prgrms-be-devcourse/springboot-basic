package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryVoucherRepository implements
    VoucherRepository {

  private final Map<UUID, Voucher> storage = new LinkedHashMap<>();

  @Override
  public UUID insert(Voucher voucher) {
    UUID voucherId = voucher.getVoucherId();
    storage.put(voucherId, voucher);
    return voucherId;
  }

  @Override
  public Optional<Voucher> getVoucher(UUID voucherId) {

    return Optional.ofNullable(storage.get(voucherId));
  }

  @Override
  public Map<UUID, Voucher> getAllVouchers() {
    return Collections.unmodifiableMap(storage);
  }


}
