package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.voucher.domain.Voucher;
import java.util.List;
import java.util.UUID;

public interface VoucherStorage {

  UUID save(Voucher voucher);
  List<Voucher> findAll();
}
