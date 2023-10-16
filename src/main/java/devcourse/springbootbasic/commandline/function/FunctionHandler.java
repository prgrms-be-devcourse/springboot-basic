package devcourse.springbootbasic.commandline.function;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.constant.ConsoleConstants;
import devcourse.springbootbasic.commandline.constant.InputMessage;
import devcourse.springbootbasic.commandline.domain.VoucherTypeSelector;
import devcourse.springbootbasic.controller.UserController;
import devcourse.springbootbasic.controller.VoucherController;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.dto.UserFindResponse;
import devcourse.springbootbasic.dto.VoucherCreateRequest;
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
    private final UserController userController;
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

        Voucher voucher = voucherController.createVoucher(new VoucherCreateRequest(voucherType, amount));
        log.info(String.format(ConsoleConstants.VOUCHER_CREATE_MESSAGE, voucher.getId()));
    }

    public void listAllVouchers() {
        List<VoucherFindResponse> voucherList = voucherController.findAllVouchers();

        consoleIOHandler.printListString(voucherList);
    }

    public void listAllBlacklistedUsers() {
        List<UserFindResponse> userList = userController.findAllBlacklistedUsers();

        consoleIOHandler.printListString(userList);
    }
}
