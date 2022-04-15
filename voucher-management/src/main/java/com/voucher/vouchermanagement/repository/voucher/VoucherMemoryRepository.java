package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {

  private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();

  @Override
  public void insert(Voucher voucher) {
    store.put(voucher.getVoucherId(), voucher);
  }

  @Override
  public List<Voucher> findAll() {
    return new ArrayList<>(store.values());
  }
}
