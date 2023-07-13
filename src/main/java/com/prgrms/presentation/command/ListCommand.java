package com.prgrms.presentation.command;

import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.presentation.view.ViewManager;
import com.prgrms.service.voucher.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListCommand implements Command {

    private VoucherService voucherService;

    public ListCommand(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public Power execute(ViewManager viewManager) {
        List<VoucherResponse> vouchers = voucherService.getAllVoucherList();
        viewManager.viewVoucherList(vouchers);

        return Power.ON;
    }
}
