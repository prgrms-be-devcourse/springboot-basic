package com.example.commandlineapplication.domain.voucher.repository;

import com.example.commandlineapplication.domain.voucher.Voucher;
import com.example.commandlineapplication.domain.voucher.VoucherType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository {

  Optional<Voucher> findById(UUID voucherId);

  Voucher insert(Voucher voucher);

  List<Voucher> findAll();

  void deleteById(UUID voucherId);

  List<Voucher> findVouchersByVoucherType(VoucherType voucherType);
}
