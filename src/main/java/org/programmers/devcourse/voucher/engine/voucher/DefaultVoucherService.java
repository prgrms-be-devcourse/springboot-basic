package org.programmers.devcourse.voucher.engine.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultVoucherService implements VoucherService {

  private final VoucherRepository voucherRepository;

  public DefaultVoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  @Override
  public Voucher create(String voucherTypeId, long voucherDiscountData) throws VoucherException {
    var voucherType = VoucherType.from(voucherTypeId)
        .orElseThrow(() -> new VoucherException("Invalid Voucher Type Id"));
    var voucher = voucherType.createVoucher(UUID.randomUUID(), voucherDiscountData,
        LocalDateTime.now());
    voucherRepository.save(voucher);
    return voucher;
  }

  @Override
  public List<Voucher> getAllVouchers() {
    return voucherRepository.getAllVouchers();
  }

  @Override
  public Optional<VoucherType> mapTypeToMapper(String type) {
    return VoucherType.from(type);
  }

  @Override
  public void remove(UUID voucherId) {
    voucherRepository.delete(voucherId);
  }

  @Override
  public Voucher getVoucherById(UUID voucherId) {
    return voucherRepository.getVoucherById(voucherId)
        .orElseThrow(() -> new VoucherException("No Voucher Available"));
  }

  @Override
  public List<Voucher> getVouchersByType(String type) {
    return voucherRepository.getVouchersByType(type);
  }

  @Override
  public List<Voucher> getVouchersByCreatedAt(LocalDateTime date) {
    return voucherRepository.getAllVouchers().stream()
        .filter(voucher -> voucher.getCreatedAt().isAfter(date))
        .collect(Collectors.toList());
  }
}
