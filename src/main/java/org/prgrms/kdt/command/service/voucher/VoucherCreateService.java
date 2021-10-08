package org.prgrms.kdt.command.service.voucher;

import org.prgrms.kdt.command.ValueValidation;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.command.service.CommandService;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

@Service
public class VoucherCreateService implements CommandService {

    private final VoucherService voucherService;

    public VoucherCreateService(final VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void commandRun() {
        final Voucher voucher;

        Output.voucherChooseMessage();

        final String voucherType = createVoucherType();
        final long voucherDiscountValue = createVoucherDiscountValue(voucherType);
        final String customerEmail = getEmail();

        voucher = voucherService.createVoucher(voucherType, voucherDiscountValue);
        voucherService.setCustomerEmail(voucher, customerEmail);
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

    public String getEmail() {
        System.out.println("email을 입력해주세요.");
        String email;

        while (true) {
            email = Input.input();
            if (ValueValidation.emailValidation(email))
                return email;
        }
    }
}
