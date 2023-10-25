package devcourse.springbootbasic.commandline.function;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.constant.ConsoleConstants;
import devcourse.springbootbasic.commandline.constant.InputMessage;
import devcourse.springbootbasic.commandline.domain.VoucherTypeSelector;
import devcourse.springbootbasic.controller.CustomerController;
import devcourse.springbootbasic.controller.VoucherController;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.dto.customer.CustomerCreateRequest;
import devcourse.springbootbasic.dto.customer.CustomerFindResponse;
import devcourse.springbootbasic.dto.customer.CustomerResponse;
import devcourse.springbootbasic.dto.customer.CustomerUpdateBlacklistRequest;
import devcourse.springbootbasic.dto.voucher.VoucherCreateRequest;
import devcourse.springbootbasic.dto.voucher.VoucherFindResponse;
import devcourse.springbootbasic.dto.voucher.VoucherResponse;
import devcourse.springbootbasic.dto.voucher.VoucherUpdateDiscountValueRequest;
import devcourse.springbootbasic.exception.InputErrorMessage;
import devcourse.springbootbasic.exception.InputException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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

        VoucherResponse voucher = voucherController.createVoucher(new VoucherCreateRequest(voucherType, amount));
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

    public void createCustomer() {
        String name = consoleIOHandler.inputStringWithMessage(InputMessage.CUSTOMER_NAME);

        CustomerResponse customer = customerController.save(new CustomerCreateRequest(name));
        log.info(String.format(ConsoleConstants.CUSTOMER_CREATE_MESSAGE, customer.getId()));
    }

    public void updateBlacklistStatus() {
        UUID customerId = consoleIOHandler.inputUUIDWithMessage(InputMessage.CUSTOMER_ID);
        boolean isBlacklisted = consoleIOHandler.inputBooleanWithMessage(InputMessage.BLACKLIST_STATUS);

        CustomerResponse customer = customerController.updateBlacklistStatus(new CustomerUpdateBlacklistRequest(customerId, isBlacklisted));
        log.info(String.format(ConsoleConstants.CUSTOMER_UPDATE_MESSAGE, customer.getId()));
    }

    public void updateDiscountValue() {
        UUID voucherId = consoleIOHandler.inputUUIDWithMessage(InputMessage.VOUCHER_ID);
        long discountValue = consoleIOHandler.inputLongWithMessage(InputMessage.DISCOUNT_VALUE);

        VoucherResponse voucher = voucherController.updateDiscountValue(new VoucherUpdateDiscountValueRequest(voucherId, discountValue));
        log.info(String.format(ConsoleConstants.VOUCHER_UPDATE_MESSAGE, voucher.getId()));
    }

    public void deleteVoucher() {
        UUID voucherId = consoleIOHandler.inputUUIDWithMessage(InputMessage.VOUCHER_ID);

        VoucherResponse voucher = voucherController.deleteVoucher(voucherId);
        log.info(String.format(ConsoleConstants.VOUCHER_DELETE_MESSAGE, voucher.getId()));
    }
}
