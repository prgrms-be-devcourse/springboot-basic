package com.prgrms.presentation.command;

import com.prgrms.dto.voucher.VoucherRequest;
import com.prgrms.service.voucher.VoucherService;
import com.prgrms.presentation.view.ViewManager;
import org.springframework.stereotype.Component;

@Component
public class CreateCommand implements Command {

    private VoucherService voucherService;

    public CreateCommand(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public Power execute(ViewManager viewManager) {
        VoucherRequest voucherRequest = viewManager.guideCreateVoucher();
        voucherService.createVoucher(voucherRequest);

        return Power.ON;
    }
}
