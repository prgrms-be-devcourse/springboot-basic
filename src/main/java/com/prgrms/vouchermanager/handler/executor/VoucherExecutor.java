package com.prgrms.vouchermanager.handler.executor;

import com.prgrms.vouchermanager.controller.VoucherConsoleController;
import com.prgrms.vouchermanager.controller.VoucherWebController;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.dto.VoucherRequest;
import com.prgrms.vouchermanager.dto.VoucherResponse;
import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.exception.NotCorrectFormException;
import com.prgrms.vouchermanager.exception.NotCorrectIdException;
import com.prgrms.vouchermanager.exception.NotCorrectScopeException;
import com.prgrms.vouchermanager.io.ConsolePrint;
import com.prgrms.vouchermanager.io.ConsoleReader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.dto.VoucherRequest.*;
import static com.prgrms.vouchermanager.dto.VoucherResponse.*;
import static com.prgrms.vouchermanager.message.ConsoleMessage.*;

@Component
public class VoucherExecutor {

    private final ConsoleReader consoleReader;
    private final ConsolePrint consolePrint;
    private final VoucherConsoleController controller;

    public VoucherExecutor(ConsoleReader consoleReader, ConsolePrint consolePrint, VoucherConsoleController controller) {
        this.consoleReader = consoleReader;
        this.consolePrint = consolePrint;
        this.controller = controller;
    }

    public void create() {
        VoucherType voucherType = getVoucherType();
        int discount = getVoucherDiscount(voucherType);

        controller.create(new VoucherCreateRequest(voucherType.getLabel(), discount));
        consolePrint.printMessage(COMPLETE_CREATE_VOUCHER.getMessage());
    }

    public void list() throws EmptyListException {
        VoucherAllListResponse response = controller.findAll();
        List<Voucher> vouchers = response.vouchers();
        if(vouchers.isEmpty()) throw new EmptyListException(vouchers);
        else consolePrint.printList(vouchers);
    }

    public void delete() {
        consolePrint.printMessage(GET_VOUCHER_ID.getMessage());
        try {
            UUID id = UUID.fromString(consoleReader.readString());
            controller.delete(id);
        } catch (IllegalArgumentException e) {
            throw new NotCorrectIdException();
        }
        consolePrint.printMessage(COMPLETE_DELETE_CUSTOMER.getMessage());
    }

    public void update() {
        consolePrint.printMessage(GET_VOUCHER_ID.getMessage());
        UUID id = UUID.fromString(consoleReader.readString());
        consolePrint.printMessage(GET_CUSTOMER_YEAR.getMessage());
        int discount = 0;
        try {
            discount = consoleReader.readInt();
            controller.updateDiscount(id, discount);
        } catch (NumberFormatException e) {
            throw new NotCorrectFormException(String.valueOf(discount));
        }
        consolePrint.printMessage(COMPLETE_UPDATE_CUSTOMER.getMessage());
    }

    public VoucherType getVoucherType() throws NotCorrectFormException {
        consolePrint.printMessage(GET_VOUCHER_TYPE.getMessage());
        String type = consoleReader.readString();

        try {
            if (type.equals("fixed")) {
                return VoucherType.FIXED;
            } else if (type.equals("percent")) {
                return VoucherType.PERCENT;
            } else {
                throw new NotCorrectFormException(type);
            }
        } catch (NotCorrectFormException e) {
            throw new NotCorrectFormException(type);
        }
    }

    private int getVoucherDiscount(VoucherType type) throws NotCorrectScopeException, NotCorrectFormException {
        int discount = 0;
        try {
            if (type == VoucherType.FIXED) {
                discount = getFixedDiscount();
            } else if (type == VoucherType.PERCENT) {
                discount = getPercentDiscount();
            }
        } catch (NumberFormatException e) {
            throw new NotCorrectFormException(String.valueOf(discount));
        } catch (NotCorrectScopeException e) {
            throw new NotCorrectScopeException(discount);
        }

        return discount;
    }

    private int getPercentDiscount() throws NotCorrectScopeException {
        int discount = 0;
        try {
            consolePrint.printMessage(GET_DISCOUNT_PERCENT.getMessage());
            discount = consoleReader.readInt();
            if (discount < 0 || discount > 100) throw new NotCorrectScopeException(discount);
            return discount;
        } catch (NumberFormatException e) {
            throw new NotCorrectFormException(String.valueOf(discount));
        }
    }

    private int getFixedDiscount() throws NotCorrectScopeException {
        int discount = 0;
        try {
            consolePrint.printMessage(GET_DISCOUNT_AMOUNT.getMessage());
            discount = consoleReader.readInt();
            if (discount < 0) throw new NotCorrectScopeException(discount);
            return discount;
        } catch (NumberFormatException e) {
            throw new NotCorrectFormException(String.valueOf(discount));
        }
    }
}
