package com.prgrms.commandLineApplication.repository;

import com.prgrms.commandLineApplication.voucher.Voucher;

import java.util.List;

public interface VoucherRepository {

  void save(Voucher voucher);

  List<Voucher> findAll();

}
