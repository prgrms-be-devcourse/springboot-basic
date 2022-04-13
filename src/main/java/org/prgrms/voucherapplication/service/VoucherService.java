package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.FixedAmountVoucher;
import org.prgrms.voucherapplication.entity.PercentDiscountVoucher;
import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.exception.NoSuchVoucherException;
import org.prgrms.voucherapplication.repository.VoucherRepository;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private VoucherRepository voucherRepository;

    public VoucherService(@Qualifier("fileVoucherRepository") VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void saveVoucher(Voucher voucher) throws IOException {
        voucherRepository.insert(voucher);
    }

    public Voucher getVoucher(UUID voucherId) throws IOException{
        return voucherRepository.findById(voucherId).orElseThrow(() -> new NoSuchVoucherException(voucherId));
    }

    public List<Voucher> getAllVoucher() throws IOException{
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(VoucherType voucherType, long discountValue) {
        UUID voucherId = UUID.randomUUID();
        if (voucherType.equals(VoucherType.FixedAmount)) {
            return createFixedAmountVoucher(voucherId, discountValue);
        } else {
            return createPercentDiscountVoucher(voucherId, discountValue);
        }
    }

    private FixedAmountVoucher createFixedAmountVoucher(UUID voucherId, long amount) {
        return new FixedAmountVoucher(voucherId, amount);
    }

    private PercentDiscountVoucher createPercentDiscountVoucher(UUID voucherId, long percent) {
        return new PercentDiscountVoucher(voucherId, percent);
    }
}
