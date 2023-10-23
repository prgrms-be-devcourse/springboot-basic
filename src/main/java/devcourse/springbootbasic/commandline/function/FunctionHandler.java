package devcourse.springbootbasic.commandline.function;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.constant.ConsoleConstants;
import devcourse.springbootbasic.commandline.constant.InputMessage;
import devcourse.springbootbasic.commandline.domain.VoucherTypeSelector;
import devcourse.springbootbasic.controller.CustomerController;
import devcourse.springbootbasic.controller.VoucherController;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.dto.CustomerFindResponse;
import devcourse.springbootbasic.dto.VoucherCreateRequest;
import devcourse.springbootbasic.dto.VoucherCreateResponse;
import devcourse.springbootbasic.dto.VoucherFindResponse;
import devcourse.springbootbasic.exception.InputErrorMessage;
import devcourse.springbootbasic.exception.InputException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FunctionHandler {

    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final ConsoleIOHandler consoleIOHandler;

    public void exit() {
        System.exit(0);
    }

    public void createVoucher() {
        consoleIOHandler.printEnumString(VoucherTypeSelector.class);

        String voucherTypeCode = consoleIOHandler.inputStringWithMessage(InputMessage.VOUCHER_TYPE);
        VoucherType voucherType = VoucherTypeSelector
                .fromString(voucherTypeCode)
                .orElseThrow(() -> InputException.of(InputErrorMessage.INVALID_VOUCHER_TYPE))
                .getVoucherType();

        long amount = consoleIOHandler.inputLongWithMessage(InputMessage.DISCOUNT_VALUE);

        VoucherCreateResponse voucher = voucherController.createVoucher(new VoucherCreateRequest(voucherType, amount));
        log.info(String.format(ConsoleConstants.VOUCHER_CREATE_MESSAGE, voucher.getId()));
    }

    public void listAllVouchers() {
        List<VoucherFindResponse> voucherList = voucherController.findAllVouchers();

        consoleIOHandler.printListString(voucherList);
    }

    public void findAllBlacklistedCustomers() {
        List<CustomerFindResponse> customerList = customerController.findAllBlacklistedCustomers();

        consoleIOHandler.printListString(customerList);
    }
}
