package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.domain.voucher.MenuType;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void create(MenuType menuType) {
        if(menuType == MenuType.FIXED){
            voucherService.createVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10, MenuType.FIXED));
        }
        else{
            voucherService.createVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 10, MenuType.PERCENT));
        }
    }
    public List<Voucher> findAllVoucher() {
        return voucherService.getAllVoucher();
    }
    public Optional<Voucher> findById(UUID voucherId){
        return voucherService.findById(voucherId);
    }
}
