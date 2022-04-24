package org.programs.kdt.Voucher.service;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Exception.DuplicationException;
import org.programs.kdt.Exception.EntityNotFoundException;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.programs.kdt.Voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public Voucher insert(Voucher voucher) {
    boolean isId = voucherRepository.existId(voucher.getVoucherId());
    if (!isId) {
      return voucherRepository.insert(voucher);
    } else {
      throw new DuplicationException(ErrorCode.DUPLICATION_VOUCHER_ID);
    }
  }

  public List<Voucher> findAll() {
    return voucherRepository.findAll();
  }

  public void delete(UUID uuid) {
    boolean isId = voucherRepository.existId(uuid);
    if (isId) {
      voucherRepository.delete(uuid);
    } else {
      throw new EntityNotFoundException(ErrorCode.NOT_FOUND_VOUCHER_ID);
    }
  }

  public List<Voucher> findByType(VoucherType voucherType) {
    return voucherRepository.findByType(voucherType);
  }

  public Optional<Voucher> findById(UUID voucherId) {
    return voucherRepository.findById(voucherId);
  }

  public boolean existVoucherId(UUID voucherId) {
    return voucherRepository.existId(voucherId);
  }
}
