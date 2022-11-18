package org.prgrms.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


@Repository
@Profile("dev")
public class VoucherInMemory implements Memory {

  private final Map<UUID, Voucher> voucherMemory = new ConcurrentHashMap<>();

  public Voucher save(Voucher voucher) {
    voucherMemory.put(voucher.getVoucherId(), voucher);
    return voucher;
  }

  public List<Voucher> findAll() {
    return new ArrayList<>(voucherMemory.values());
  }

}
