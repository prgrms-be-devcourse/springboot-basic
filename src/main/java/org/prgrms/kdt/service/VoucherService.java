package org.prgrms.kdt.service;

import java.util.List;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public List<Voucher> findAll() {
    return voucherRepository.findAll();
  }

  public Voucher create(VoucherType voucherType, Long amount) {
    var voucher = voucherType.create(amount);
    return voucherRepository.save(voucher);
  }
}