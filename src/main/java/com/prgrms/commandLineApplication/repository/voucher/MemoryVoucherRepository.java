package com.prgrms.commandLineApplication.repository.voucher;

import com.prgrms.commandLineApplication.voucher.Voucher;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {

  private static final String NO_EXIST_VOUCHER = "It doesn't exist";
  private static final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

  @Override
  public Voucher save(Voucher voucher) {
    voucherStorage.put(voucher.getVoucherId(), voucher);
    return voucher;
  }

  @Override
  public List<Voucher> findAll() {
    return voucherStorage.values()
            .stream()
            .toList();
  }

  @Override
  public Voucher findById(UUID id) {
    try {
      return voucherStorage.get(id);
    } catch (Exception e) {
      throw new NoSuchElementException(NO_EXIST_VOUCHER + " -> " + id);
    }
  }

}
