package com.devcourse.springbootbasic.application.global.io;

import com.devcourse.springbootbasic.application.domain.voucher.dto.DiscountValue;
import com.devcourse.springbootbasic.application.global.model.ListMenu;
import com.devcourse.springbootbasic.application.global.model.Menu;
import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InputConsole {
    private static final TextIO textIO = TextIoFactory.getTextIO();

    private final ConfigurableApplicationContext applicationContext;

    InputConsole(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Menu readMenu() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Menu Selection: ");
        return Menu.getMenu(input);
    }

    public VoucherType readVoucherType() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("Voucher Type Selection: ");
        return VoucherType.getVoucherType(input);
    }

    public ListMenu readListMenu() {
        String input = textIO.newStringInputReader()
                .withInputTrimming(true)
                .read("List Type Selection: ");
        return ListMenu.getListMenu(input);
    }

    public double readDiscountValue(VoucherType voucherType) {
        double input = textIO.newDoubleInputReader()
                .withInputTrimming(true)
                .read("Discount Value: ");
        return new DiscountValue(voucherType, input).getValue();
    }

}
