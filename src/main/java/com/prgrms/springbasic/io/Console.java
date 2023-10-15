package com.prgrms.springbasic.io;

import com.prgrms.springbasic.common.MenuType;
import com.prgrms.springbasic.domain.customer.dto.CustomerResponse;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Console implements Output, Input {

    TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public void printConsoleMessage(ConsoleMessage consoleMessage) {
        System.out.print(consoleMessage.getMessage());
    }

    @Override
    public String inputMenuType() {
        return textIO.newStringInputReader()
                .withIgnoreCase()
                .withPossibleValues(MenuType.allowedMenuTypes())
                .read(ConsoleMessage.CHOOSE_MENU_TYPE.getMessage());
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
    public void printVouchers(List<VoucherResponse> vouchers) {
        if (vouchers.isEmpty()) {
            textIO.getTextTerminal().println(ConsoleMessage.NO_VOUCHER_EXIST.getMessage());
            return;
        }
        for (VoucherResponse voucher : vouchers) {
            textIO.getTextTerminal().println(voucher.toString());
        }
    }

    @Override
    public void printCustomers(List<CustomerResponse> customers) {
        if (customers.isEmpty()) {
            textIO.getTextTerminal().println(ConsoleMessage.NO_VOUCHER_EXIST.getMessage());
            return;
        }
        for (CustomerResponse customer : customers) {
            textIO.getTextTerminal().println(customer.toString());
        }
    }
}
