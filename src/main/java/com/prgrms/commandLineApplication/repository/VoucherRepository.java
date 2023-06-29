package com.prgrms.commandLineApplication.repository;

import com.prgrms.commandLineApplication.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {

  void save(Voucher voucher);

  List<Voucher> findAll();

  Voucher findById(UUID id);

}
