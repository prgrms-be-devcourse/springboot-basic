package com.voucher.vouchermanagement.repository.voucher;

import com.voucher.vouchermanagement.model.voucher.Voucher;
import java.io.IOException;
import java.util.List;

public interface VoucherRepository {

  void save(Voucher voucher) throws IOException;

  List<Voucher> findAll() throws IOException;
}
