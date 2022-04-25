package org.prgrms.kdt.service;

import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.type.VoucherType;
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
    var voucherDto = new VoucherDto(UUID.randomUUID(), null, amount);
    var voucher = voucherType.create(voucherDto);
    return voucherRepository.save(voucher).orElseThrow(EntityNotFoundException::new);
  }

  public Voucher assign(UUID voucherId, UUID customerId) {
    var voucher = voucherRepository.findById(voucherId)
        .orElseThrow(EntityNotFoundException::new);
    voucher.assign(customerId);
    return voucherRepository.update(voucher).orElseThrow(EntityNotFoundException::new);
  }

  public void delete(UUID voucherId, UUID customerId) {
    voucherRepository.delete(voucherId, customerId);
  }

  public List<Voucher> findByCustomerId(UUID customerId) {
    return voucherRepository.findByCustomerId(customerId);
  }
}