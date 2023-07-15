package com.prgrms.commandLineApplication.repository.voucher;

import com.prgrms.commandLineApplication.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherRepository {

  Voucher save(Voucher voucher);

  List<Voucher> findAll();

  Voucher findById(UUID id);

}
