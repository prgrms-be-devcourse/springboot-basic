package com.devcourse.springbootbasic.application.template;

import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.ListMenu;
import com.devcourse.springbootbasic.application.dto.Menu;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.dto.VoucherType;
import com.devcourse.springbootbasic.application.io.InputConsole;
import com.devcourse.springbootbasic.application.io.OutputConsole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLineTemplate {

    private final InputConsole input;
    private final OutputConsole output;

    public CommandLineTemplate(InputConsole input, OutputConsole output) {
        this.input = input;
        this.output = output;
    }

    public VoucherDto createVoucherDto() {
        output.showVoucherType();
        VoucherType voucherType = input.readVoucherType();
        double value = input.readDiscountValue(voucherType);
        return new VoucherDto(voucherType, value);
    }

    public void printVoucher(Voucher voucher) {
        output.printVoucher(voucher);
    }

    public ListMenu listTask() {
        output.showListMenu();
        return input.readListMenu();
    }

    public void printVouchers(List<String> list) {
        output.printVouchers(list);
    }

    public Menu menuTask() {
        output.showMenu();
        return input.readMenu();
    }

    public void errorTask(Exception e) {
        output.printError(e);
    }

    public void endGameTask() {
        output.endPlatform();
    }
}
