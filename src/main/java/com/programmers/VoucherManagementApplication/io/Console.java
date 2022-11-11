package com.programmers.VoucherManagementApplication.io;

import com.programmers.VoucherManagementApplication.voucher.Voucher;
import java.util.List;

public class Console {
    private Input input;
    private Output output;

    public Console(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String input() {
        return input.input();
    }
    public void menuPromptPrint() {
        output.messagePrint(Message.MENU_PROMPT);
    }
    public void invalidInputPrint() {
        output.messagePrint(Message.INVALID_INPUT);
    }
    public void exitMenuPrint() {
        output.messagePrint(Message.EXIT_MENU);
    }
    public void selectVoucherMenu() {
        output.messagePrint(Message.VOUCHER_MENU);
    }
    public void inputPricePrompt() {
        output.messagePrint(Message.PRICE_INPUT_PROMPT);
    }
    public void inputVoucherAmount(Message message) {
        output.messagePrint(message);
    }

    public void findAll(List<Voucher> list) {
        for (Voucher voucher : list) {
            System.out.println(voucher.getVoucherType() + " : " + voucher.getOriginPrice() + " | " + voucher.getAmount() + " | " + voucher.discount());
        }
    }
}
