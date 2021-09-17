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

    /**
     * voucherId 에 해당하는 바우처 조회
     *
     * @param voucherId
     * @return 조회한 바우처 반환
     */
    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    /**
     * type 에 따른 Voucher 생성
     *
     * @param type
     * @param discountValue
     * @return 생성된 바우처 반환
     */
    public Voucher createVoucher(String type, UUID voucherId, long discountValue) {
        Voucher voucher = VoucherType.getVoucherType(type, voucherId, discountValue);
        voucherRepository.insert(voucher);
        return voucher;
    }

    /**
     * @return 모든 바우처 반환
     */
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
