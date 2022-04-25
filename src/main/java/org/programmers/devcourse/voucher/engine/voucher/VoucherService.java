package org.programmers.devcourse.voucher.engine.voucher;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

  private final VoucherRepository voucherRepository;

  public VoucherService(VoucherRepository voucherRepository) {
    this.voucherRepository = voucherRepository;
  }

  public UUID create(String voucherTypeId, long voucherDiscountData) throws VoucherException {

    // validation
    var voucherType = VoucherType.from(voucherTypeId).orElseThrow(() -> new VoucherException("Invalid Voucher Type Id"));

    var voucher = voucherType.getFactory().create(UUID.randomUUID(), voucherDiscountData);
    voucherRepository.save(voucher);
    return voucher.getVoucherId();
  }

  public Collection<Voucher> getAllVouchers() {
    return voucherRepository.getAllVouchers();
  }

  public Optional<VoucherType> mapTypeToMapper(String type) {
    return VoucherType.from(type);
  }

  public void remove(UUID voucherId) {
    voucherRepository.delete(voucherId);
  }

  public Voucher getVoucherById(UUID voucherId) {
    return voucherRepository.getVoucherById(voucherId).orElseThrow(() -> new VoucherException("No Voucher Available"));
  }
}
