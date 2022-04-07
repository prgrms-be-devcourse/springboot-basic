package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class VoucherMemoryRepository implements VoucherRepository {

  private final Map<UUID, Voucher> store = new ConcurrentHashMap<>();
  private static final Logger logger = LoggerFactory.getLogger(VoucherMemoryRepository.class);

  @Override
  public void save(Voucher voucher) {
    logger.info("save()");
    store.put(voucher.getVoucherId(), voucher);
  }

  @Override
  public List<Voucher> findAll() {
    logger.info("findAll()");

    return new ArrayList<>(store.values());
  }
}
