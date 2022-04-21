package com.prgms.management.voucher.service;

import com.prgms.management.voucher.entity.Voucher;
import com.prgms.management.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleVoucherService implements VoucherService {
  private final VoucherRepository voucherRepository;
  
  public SimpleVoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }
  
  public List<Voucher> getAllVouchers() {
    return voucherRepository.findAll();
  }
  
  public Voucher saveVoucher(Voucher voucher) {
    return voucherRepository.save(voucher);
  }
}
