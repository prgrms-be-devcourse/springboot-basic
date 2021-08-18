package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.repository.VoucherRepository;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findId(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }

    public Voucher createVoucher(VoucherType voucherType, UUID voucherId, long discount){
        if(voucherType == VoucherType.FIXED_AMOUNT){
            return new FixedAmountVoucher(voucherId, discount);
        }
        else //voucherType == VoucherType.PERCENT_DISCOUNT
        {
            return new PercentDiscountVoucher(voucherId, discount);
        }
    }

    public void addVoucher(Voucher voucher){
        voucherRepository.insert(voucher);
    }

    public List<Voucher> getAllVouchers(){
        return voucherRepository.getAllVouchers();
    }
}
