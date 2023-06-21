package com.devcourse.springbootbasic.engine.io;

import com.devcourse.springbootbasic.engine.model.Menu;
import com.devcourse.springbootbasic.engine.model.VoucherType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

@Component
public class InputConsole {

    private TextIO input = TextIoFactory.getTextIO();

    public Menu inputMenu() {
        return input.newEnumInputReader(Menu.class)
                .read();
    }

    public VoucherType inputVoucherType() {
        return input.newEnumInputReader(VoucherType.class)
                .read();
    }

    public double inputVoucherDiscount() {
        return input.newDoubleInputReader()
                .read();
    }
}
