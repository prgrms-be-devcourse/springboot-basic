package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.controller.FixedAmountVoucherDto;
import org.prgrms.kdt.voucher.controller.PercentDiscountVoucherDto;
import org.prgrms.kdt.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;
  private List<Voucher> voucherList = new ArrayList<>();

  public VoucherService(@Qualifier("memory") VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public Voucher getVoucher(UUID voucherId) {
    return voucherRepository
      .findById(voucherId)
      .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
  }

  public void useVoucher(Voucher voucher) {
  }

  public void createVoucher(FixedAmountVoucherDto fixedAmountVoucherDto) {
    var voucher = new FixedAmountVoucher(fixedAmountVoucherDto.getVoucherId(), fixedAmountVoucherDto.getAmount());
    voucherRepository.insert(voucher);
  }

  public void createVoucher(PercentDiscountVoucherDto percentDiscountVoucherDto) {
    var voucher = new PercentDiscountVoucher(percentDiscountVoucherDto.getVoucherId(), percentDiscountVoucherDto.getPercent());
    voucherRepository.insert(voucher);
  }

  public List<Voucher> getAllVouchers() {
    return (List<Voucher>) voucherRepository.findAll();
  }
}
