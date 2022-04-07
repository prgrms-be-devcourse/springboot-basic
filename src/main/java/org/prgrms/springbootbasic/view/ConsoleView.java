package org.prgrms.springbootbasic.view;

import static org.prgrms.springbootbasic.view.ConstantString.AMOUNT;
import static org.prgrms.springbootbasic.view.ConstantString.CREATE;
import static org.prgrms.springbootbasic.view.ConstantString.EXIT;
import static org.prgrms.springbootbasic.view.ConstantString.LIST;
import static org.prgrms.springbootbasic.view.ConstantString.PERCENT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_AMOUNT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_MENU;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_PERCENT;
import static org.prgrms.springbootbasic.view.ConstantString.SELECT_VOUCHER_TYPE;
import static org.prgrms.springbootbasic.view.ConstantString.TO_CREATE_A_NEW_VOUCHER;
import static org.prgrms.springbootbasic.view.ConstantString.TO_EXIT_THE_PROGRAM;
import static org.prgrms.springbootbasic.view.ConstantString.TO_LIST_ALL_VOUCHERS;
import static org.prgrms.springbootbasic.view.ConstantString.TYPE;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_ID;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_LIST;
import static org.prgrms.springbootbasic.view.ConstantString.VOUCHER_PROGRAM;

import java.util.List;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.service.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleView {

    private final TextIO textIO = TextIoFactory.getTextIO();

    public void printMenu() {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println(VOUCHER_PROGRAM);
        terminal.print(TYPE);
        terminal.executeWithPropertiesConfigurator(
            props -> props.setPromptBold(true),
            t -> t.print(EXIT));
        terminal.println(TO_EXIT_THE_PROGRAM);

        terminal.print(TYPE);
        terminal.executeWithPropertiesConfigurator(
            props -> props.setPromptBold(true),
            t -> t.print(CREATE));
        terminal.println(TO_CREATE_A_NEW_VOUCHER);

        terminal.print(TYPE);
        terminal.executeWithPropertiesConfigurator(
            props -> props.setPromptBold(true),
            t -> t.print(LIST));
        terminal.println(TO_LIST_ALL_VOUCHERS);

        terminal.println();
    }

    public Menu inputMenu() {
        Menu menu = textIO.newEnumInputReader(Menu.class)
            .read(SELECT_MENU);
        textIO.getTextTerminal().println();
        return menu;
    }

    public VoucherType selectVoucherType() {
        VoucherType voucherType = textIO.newEnumInputReader(VoucherType.class)
            .read(SELECT_VOUCHER_TYPE);
        textIO.getTextTerminal().println();
        return voucherType;
    }

    public long selectAmount() {
        long amount = textIO.newLongInputReader()
            .read(SELECT_AMOUNT);
        textIO.getTextTerminal().println();
        return amount;
    }

    public int selectPercent() {
        int percent = textIO.newIntInputReader()
            .withMaxVal(100)
            .read(SELECT_PERCENT);
        textIO.getTextTerminal().println();
        return percent;
    }

    public void printList(List<Voucher> vouchers) {
        TextTerminal<?> terminal = textIO.getTextTerminal();
        terminal.println(VOUCHER_LIST);
        terminal.println();

        for (Voucher voucher : vouchers) {
            printVoucher(terminal, voucher);
        }
        terminal.println();
    }

    private void printVoucher(TextTerminal<?> terminal, Voucher voucher) {
        terminal.print(VOUCHER_ID + voucher.getVoucherId());

        if (voucher.getClass() == FixedAmountVoucher.class) {
            var fixedAmountVoucher = (FixedAmountVoucher) voucher;
            terminal.println(AMOUNT + fixedAmountVoucher.getAmount());
        }
        if (voucher.getClass() == PercentDiscountVoucher.class) {
            var percentDiscountVoucher = (PercentDiscountVoucher) voucher;
            terminal.println(PERCENT + percentDiscountVoucher.getPercent());
        }
    }

}
