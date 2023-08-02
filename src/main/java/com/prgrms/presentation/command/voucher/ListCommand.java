package com.prgrms.presentation.command.voucher;

import com.prgrms.presentation.Power;
import com.prgrms.presentation.command.Command;
import com.prgrms.presentation.view.Output;
import com.prgrms.voucher.service.VoucherService;
import com.prgrms.voucher.service.dto.VoucherServiceListRequest;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import java.util.List;
import org.springframework.stereotype.Component;

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

        List<VoucherServiceResponse> vouchers = voucherService.getAllVoucherList(new VoucherServiceListRequest(null, null));
        vouchers.forEach(v -> output.write(v.toString()));

        return Power.ON;
    }

}
