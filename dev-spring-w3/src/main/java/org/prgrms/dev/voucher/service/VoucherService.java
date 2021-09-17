package org.prgrms.dev.voucher.service;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.prgrms.dev.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public Voucher createVoucher(String type, UUID voucherId, long discountValue) {
        Voucher voucher = VoucherType.getVoucherType(type, voucherId, discountValue);
        return voucherRepository.insert(voucher);
    }

    public List<Voucher> listVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher updateVoucherDiscount(UUID voucherId, long newDiscount) {
        Voucher voucher = getVoucher(voucherId);
        Voucher updateVoucher = VoucherType.getVoucherType(voucher.getVoucherType().toString(), voucher.getVoucherId(), newDiscount);
        return voucherRepository.update(updateVoucher);
    }

    public void deleteVoucher(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public void useVoucher(Voucher voucher) {
        // TODO:
    }
}
