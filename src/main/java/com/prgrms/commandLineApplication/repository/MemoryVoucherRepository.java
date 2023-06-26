package com.prgrms.commandLineApplication.repository;

import com.prgrms.commandLineApplication.voucher.Voucher;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryVoucherRepository implements VoucherRepository {

  private static final Map<UUID, Voucher> voucherStorage = new ConcurrentHashMap<>();

  @Override
  public void save(Voucher voucher) {
    voucherStorage.put(voucher.getVoucherId(), voucher);
  }

  @Override
  public Map<UUID, Voucher> findAll() {
    return voucherStorage;
  }

}
