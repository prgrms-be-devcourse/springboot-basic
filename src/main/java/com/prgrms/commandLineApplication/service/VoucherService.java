package com.prgrms.commandLineApplication.service;

import com.prgrms.commandLineApplication.repository.VoucherRepository;
import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.VoucherFactory;

import java.util.Map;
import java.util.UUID;

public class VoucherService {

  private final VoucherRepository voucherRepository;
  private final VoucherFactory voucherFactory;

  public VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
    this.voucherRepository = voucherRepository;
    this.voucherFactory = voucherFactory;
  }

  public Map<UUID, Voucher> viewList() {
    return voucherRepository.findAll();
  }

  public void create(String voucherType, int discountAmount) {
    Voucher createdVoucher = voucherFactory.createVoucher(voucherType, discountAmount);
    voucherRepository.save(createdVoucher);
  }

}
