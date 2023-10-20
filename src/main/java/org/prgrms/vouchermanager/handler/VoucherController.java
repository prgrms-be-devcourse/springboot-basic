package org.prgrms.vouchermanager.handler;

import org.prgrms.vouchermanager.repository.MemoryVoucherRepository;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.prgrms.vouchermanager.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class VoucherController {

    VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());
    public void create(VoucherType voucherType) {
        if(voucherType == VoucherType.FIXED)
            voucherService.createVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        else
            voucherService.createVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 10L));
    }

    public List<Voucher> list() {
        return voucherService.getAllVoucher();
    }
}
