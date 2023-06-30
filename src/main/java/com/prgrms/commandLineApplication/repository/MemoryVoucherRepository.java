package com.prgrms.commandLineApplication.repository;

import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.validator.VoucherValidator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryVoucherRepository implements VoucherRepository {

  private static final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

  @Override
  public void save(Voucher voucher) {
    voucherStorage.put(voucher.getVoucherId(), voucher);
  }

  @Override
  public List<Voucher> findAll() {
    return voucherStorage.values()
            .stream()
            .toList();
  }

  @Override
  public Voucher findById(UUID id) {
    VoucherValidator.checkId(id);
    return voucherStorage.get(id);
  }

}
