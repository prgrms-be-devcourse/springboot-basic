package com.prgrms.presentation.command.voucher;

import com.prgrms.dto.voucher.VoucherResponse;
import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.view.Output;
import com.prgrms.service.voucher.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListCommand implements Command {

    private final Output output;
    private final VoucherService voucherService;

    public ListCommand(Output output, VoucherService voucherService) {
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public Power execute() {
        List<VoucherResponse> vouchers = voucherService.getAllVoucherList();
        vouchers.forEach(v -> output.write(v.toString()));

        return Power.ON;
    }

}
