package com.prgmrs.voucher.view;

import com.prgmrs.voucher.dto.request.*;
import com.prgmrs.voucher.exception.NoSuchVoucherTypeException;
import com.prgmrs.voucher.view.io.ConsoleMessage;
import com.prgmrs.voucher.view.io.ConsoleReader;
import com.prgmrs.voucher.view.io.ConsoleWriter;
import com.prgmrs.voucher.view.io.ManagementType;
import org.springframework.stereotype.Component;

@Component
public class RequestBodyCreator {
    private final ConsoleWriter consoleWriter;
    private final ConsoleReader consoleReader;

    public RequestBodyCreator(ConsoleWriter consoleWriter, ConsoleReader consoleReader) {
        this.consoleWriter = consoleWriter;
        this.consoleReader = consoleReader;
    }

    public UserRequest createUserRequest() {
        consoleWriter.write(ConsoleMessage.REQUEST_USERNAME);
        String name = consoleReader.read();
        return new UserRequest(name);
    }

    public UsernameRequest createUsernameRequest() {
        consoleWriter.write(ConsoleMessage.REQUEST_USERNAME);
        String name = consoleReader.read();
        return new UsernameRequest(name);
    }

    public VoucherRequest createVoucherRequest(ManagementType managementType) {
        consoleWriter.write(ConsoleMessage.REQUEST_DISCOUNT_VALUE);
        String numberString = consoleReader.read();

        VoucherRequest voucherRequest;
        switch (managementType) {
            case FIXED_AMOUNT_VOUCHER -> voucherRequest = new VoucherRequest("fixed", numberString);
            case PERCENT_DISCOUNT_VOUCHER -> voucherRequest = new VoucherRequest("percent", numberString);
            default -> throw new NoSuchVoucherTypeException("no such voucher type");
        }
        return voucherRequest;
    }

    public AssignVoucherRequest createAssignVoucherRequest() {
        consoleWriter.write(ConsoleMessage.REQUEST_VOUCHER_ID);
        String voucherUuid = consoleReader.read();

        consoleWriter.write(ConsoleMessage.REQUEST_USERNAME);
        String username = consoleReader.read();

        return new AssignVoucherRequest(username, voucherUuid);
    }

    public RemoveVoucherRequest createRemoveVoucherRequest() {
        consoleWriter.write(ConsoleMessage.REQUEST_VOUCHER_ID);
        String voucherUuid = consoleReader.read();

        return new RemoveVoucherRequest(voucherUuid);
    }

    public VoucherIdRequest createVoucherIdRequest() {
        consoleWriter.write(ConsoleMessage.REQUEST_VOUCHER_ID);
        String voucherUuid = consoleReader.read();

        return new VoucherIdRequest(voucherUuid);
    }
}
