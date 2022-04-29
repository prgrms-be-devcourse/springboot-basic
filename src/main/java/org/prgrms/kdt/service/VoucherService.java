package org.prgrms.kdt.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.exception.EntityNotFoundException;
import org.prgrms.kdt.repository.VoucherRepository;
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

  public Voucher findById(UUID voucherId) {
    return voucherRepository.findById(voucherId)
        .orElseThrow(EntityNotFoundException::new);
  }

  public Voucher create(VoucherDto voucherDto) {
    var voucherType = voucherDto.voucherType();
    var voucher = voucherType.create(voucherDto);
    return voucherRepository.save(voucher).orElseThrow(EntityNotFoundException::new);
  }

  public Voucher assign(UUID voucherId, UUID customerId) {
    var voucher = voucherRepository.findById(voucherId)
        .orElseThrow(EntityNotFoundException::new);
    voucher.assign(customerId);
    return voucherRepository.update(voucher).orElseThrow(EntityNotFoundException::new);
  }

  public void deleteByIdAndCustomerId(UUID voucherId, UUID customerId) {
    voucherRepository.delete(voucherId, customerId);
  }

  public void deleteById(UUID voucherId) {
    voucherRepository.deleteById(voucherId);
  }

  public List<Voucher> findByCustomerId(UUID customerId) {
    return voucherRepository.findByCustomerId(customerId);
  }

  public List<Voucher> findVouchers(Integer voucherType, LocalDateTime startAt,
      LocalDateTime endAt) {
    return voucherRepository.findByTypeAndCreatedAt(voucherType, startAt, endAt);
  }
}