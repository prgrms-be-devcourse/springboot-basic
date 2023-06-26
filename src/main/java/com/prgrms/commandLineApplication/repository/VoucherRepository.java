package com.prgrms.commandLineApplication.repository;

import com.prgrms.commandLineApplication.voucher.Voucher;

import java.util.Map;
import java.util.UUID;

public interface VoucherRepository {

  void save(Voucher voucher);

  Map<UUID, Voucher> findAll();

}
