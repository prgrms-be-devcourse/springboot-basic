package com.prgms.management.voucher.service;

import com.prgms.management.voucher.entity.Voucher;

import java.util.List;

public interface VoucherService {
  List<Voucher> getAllVouchers();
  
  Voucher saveVoucher(Voucher voucher);
}
