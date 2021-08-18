package org.prgrms.kdt.kdtspringorder.voucher.service;

import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdt.kdtspringorder.voucher.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return this.voucherRepository
                .findById(voucherId)
                .orElseThrow( () -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

    public List<Voucher> getVoucherList() {
        return null;
    }

    /**
     * 새 Voucher를 생성하여 등록합니다.
     * @param type 생성할 Voucher 유형
     * @param discount 할인 받을 정도 ( 금액 or % )
     */
    public void createVoucher(VoucherType type, long discount) {

        Voucher createdVoucher;

        if(type == VoucherType.FIX) {
            createdVoucher = new FixedAmountVoucher(UUID.randomUUID(), discount);
        } else {
            createdVoucher = new PercentDiscountVoucher(UUID.randomUUID(), discount);
        }

        this.voucherRepository.insert(createdVoucher);

    }

}
