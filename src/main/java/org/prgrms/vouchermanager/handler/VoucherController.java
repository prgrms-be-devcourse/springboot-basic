package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.repository.voucher.MemoryVoucherRepository;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.prgrms.vouchermanager.repository.voucher.VoucherRepository;
import org.prgrms.vouchermanager.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());
    public void create(VoucherType voucherType) {
        if(voucherType == VoucherType.FIXED)
            voucherService.createVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        else
            voucherService.createVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 10L));
    }

    public List<Voucher> findAllVoucher() {
        return voucherService.getAllVoucher();
    }
}
