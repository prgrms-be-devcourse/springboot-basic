package org.prgrms.dev.voucher.service;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.prgrms.dev.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("memory") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    /**
     * voucherId 에 해당하는 바우처 조회
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
     * @param type
     * @param value
     * @return 생성된 바우처 반환
     */
    public Voucher createVoucher(String type, Long value) {
        Voucher voucher = VoucherType.getVoucherType(type, value);
        voucherRepository.create(voucher);
        return voucher;
    }

    /**
     * @return 모든 바우처 반환
     */
    public List<Voucher> listVoucher() {
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }
}
