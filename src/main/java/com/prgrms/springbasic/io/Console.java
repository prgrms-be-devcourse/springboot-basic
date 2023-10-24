package com.prgrms.springbasic.io;

import com.prgrms.springbasic.common.CommandType;
import com.prgrms.springbasic.common.MenuType;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class Console implements Output, Input {
    private static final TextIO textIO = TextIoFactory.getTextIO();
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


    @Override
    public void printConsoleMessage(ConsoleMessage consoleMessage) {
        textIO.getTextTerminal().print(consoleMessage.getMessage());
    }

    @Override
    public String inputMenuType() {
        return textIO.newStringInputReader()
                .withPossibleValues(MenuType.allowedMenuTypes())
                .read(ConsoleMessage.CHOOSE_COMMAND_TYPE.getMessage()).toUpperCase();
    }

    @Override
    public String inputCommandType(MenuType menuType) {
        return textIO.newStringInputReader()
                .withIgnoreCase()
                .withPossibleValues(CommandType.allowedCommandTypes(menuType))
                .read(ConsoleMessage.CHOOSE_COMMAND_TYPE.getMessage()).toUpperCase();
    }

    @Override
    public String inputDiscountType() {
        return textIO.newStringInputReader()
                .withIgnoreCase()
                .withPossibleValues(DiscountType.allowedDiscountTypes())
                .read(ConsoleMessage.GET_DISCOUNT_TYPE.getMessage());
    }

    @Override
    public long inputLong(ConsoleMessage consoleMessage) {
        return textIO.newLongInputReader()
                .read(consoleMessage.getMessage());
    }

    @Override
    public long inputPercentValue() {
        return textIO.newLongInputReader()
                .withMinVal(1L)
                .withMaxVal(100L)
                .read(ConsoleMessage.GET_PERCENT_DISCOUNT_VALUE.getMessage());
    }

    @Override
    public String inputString(ConsoleMessage consoleMessage) {
        return textIO.newStringInputReader()
                .read(consoleMessage.getMessage());
    }

    @Override
    public String inputEmail() {
        return textIO.newStringInputReader()
                .withPattern(EMAIL_PATTERN)
                .read(ConsoleMessage.GET_EMAIL.getMessage());
    }

    @Override
    public UUID inputUUID(ConsoleMessage consoleMessage) {
        String id = textIO.newStringInputReader()
                .read(consoleMessage.getMessage());
        return UUID.fromString(id);
    }

    @Override
    public void printVouchers(List<VoucherResponse> vouchers) {
        if (vouchers.isEmpty()) {
            textIO.getTextTerminal().println(ConsoleMessage.NO_VOUCHER_EXIST.getMessage());
            return;
        }
        textIO.getTextTerminal().println(ConsoleMessage.FIND_ALL_VOUCHERS.getMessage());
        for (VoucherResponse voucher : vouchers) {
            textIO.getTextTerminal().println(voucher.toString());
        }
    }

    @Override
    public void printCustomers(List<CustomerResponse> customers) {
        if (customers.isEmpty()) {
            textIO.getTextTerminal().println(ConsoleMessage.NO_CUSTOMER_EXIST.getMessage());
            return;
        }
        for (CustomerResponse customer : customers) {
            textIO.getTextTerminal().println(customer.toString());
        }
    }
}
