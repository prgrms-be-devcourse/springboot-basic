package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.ValueValidation;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

@Service
public class CommandCreateService implements CommandService {

    private final VoucherService voucherService;

    public CommandCreateService(final VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void commandRun() {
        Output.voucherChooseMessage();
        final String voucherType = createVoucherType();
        final long voucherDiscountValue = createVoucherDiscountValue(voucherType);
        voucherService.createVoucher(voucherType, voucherDiscountValue);
    }

    public String createVoucherType() {
        String voucherType;

        while (true) {
            voucherType = Input.input();
            if (ValueValidation.voucherTypeValidation(voucherType)) {
                Output.howMuchDiscountMessage(voucherType);
                return voucherType;
            }
        }
    }

    public long createVoucherDiscountValue(final String voucherType) {
        long voucherDiscountValue = 0;

        boolean inputValueCheck = false;
        do {
            final String inputValue = Input.input();
            if (ValueValidation.numberValidation(inputValue)) {
                inputValueCheck = ValueValidation.rangeValidation(voucherType, inputValue);
                voucherDiscountValue = Long.parseLong(inputValue);
            }
        } while (!inputValueCheck);

        return voucherDiscountValue;
    }
}
