package com.example.commandlineapplication.domain.voucher.repository;

import com.example.commandlineapplication.domain.voucher.model.Voucher;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
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
    return storage.values()
        .stream()
        .collect(Collectors.toList());
  }
}
