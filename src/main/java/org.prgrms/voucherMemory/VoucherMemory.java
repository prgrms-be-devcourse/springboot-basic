package org.prgrms.voucherMemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public class VoucherMemory {

  private final Map<Long, Voucher> voucherMemory = new ConcurrentHashMap<>();

  public Voucher save(Voucher voucher) {
    voucherMemory.put(Generate.id(), voucher);
    return voucher;
  }

  public List<Voucher> findAll() {
   return new ArrayList<>(voucherMemory.values());
  }
}
