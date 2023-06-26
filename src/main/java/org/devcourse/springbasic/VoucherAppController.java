package org.devcourse.springbasic;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.DomainType;
import org.devcourse.springbasic.domain.customer.controller.CustomerController;
import org.devcourse.springbasic.domain.voucher.controller.VoucherController;
import org.devcourse.springbasic.global.io.ErrorMsgPrinter;
import org.devcourse.springbasic.global.io.input.AppInput;
import org.devcourse.springbasic.global.io.input.AppInputType;
import org.devcourse.springbasic.global.io.input.customer.CustomerInput;
import org.devcourse.springbasic.global.io.input.customer.CustomerInputType;
import org.devcourse.springbasic.global.io.input.voucher.VoucherInput;
import org.devcourse.springbasic.global.io.input.voucher.VoucherInputType;
import org.devcourse.springbasic.global.io.output.AppOutput;
import org.devcourse.springbasic.global.io.output.AppOutputType;
import org.devcourse.springbasic.global.io.output.customer.CustomerOutput;
import org.devcourse.springbasic.global.io.output.customer.CustomerOutputType;
import org.devcourse.springbasic.global.io.output.voucher.VoucherOutput;
import org.devcourse.springbasic.global.io.output.voucher.VoucherOutputType;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class VoucherAppController {

    private final CustomerController customerController;
    private final VoucherController voucherController;

    public void run() {

        String environment = getEnvironment();
        AppInput input = AppInputType.valueOf(environment).getInput();
        AppOutput output = AppOutputType.valueOf(environment).getOutput();
        CustomerInput customerInput = CustomerInputType.valueOf(environment).getInput();
        CustomerOutput customerOutput = CustomerOutputType.valueOf(environment).getOutput();
        VoucherInput voucherInput = VoucherInputType.valueOf(environment).getInput();
        VoucherOutput voucherOutput = VoucherOutputType.valueOf(environment).getOutput();

        try {
            output.menus();
            DomainType selectedMenu = input.menu();

            switch (selectedMenu) {
                case CUSTOMER:
                    customerController.run(customerInput, customerOutput);
                    break;

                case VOUCHER:
                    voucherController.run(voucherInput, voucherOutput);
                    break;
            }
        } catch (IllegalArgumentException e) {
            ErrorMsgPrinter.print(e.getMessage());
        }
    }

    private String getEnvironment() {
        return "CONSOLE";
    }
}