package com.example.commandlineapplication.domain.voucher.repository;

import com.example.commandlineapplication.domain.voucher.model.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

  Optional<Voucher> findById(UUID voucherId);

  Voucher insert(Voucher voucher);

  void update(UUID voucherId);

  List<Voucher> findAll();

  void deleteById(UUID voucherId);

  void deleteAll();
}
