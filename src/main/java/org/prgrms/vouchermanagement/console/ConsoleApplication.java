package org.prgrms.vouchermanagement.console;

import org.prgrms.vouchermanagement.console.io.Input;
import org.prgrms.vouchermanagement.console.io.Menu;
import org.prgrms.vouchermanagement.console.io.Output;
import org.prgrms.vouchermanagement.service.MemberService;
import org.prgrms.vouchermanagement.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication implements ApplicationRunner {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final MemberService memberService;

    @Autowired
    public ConsoleApplication(Input input, Output output, VoucherService voucherService, MemberService memberService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.memberService = memberService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        while (true) {
            output.printMenu();
            Menu menu = input.inputMenu();

            switch (menu) {
                case EXIT_PROGRAM -> {
                    break;
                }
                case CREATE_VOUCHER -> {

                }
                case LIST_VOUCHERS -> {
                    excuteListVouchers();
                }
                case LIST_BLACK_CUSTOMERS -> {

                }
                default -> {
                    output.printMenuError();
                }
            }
        }
    }

    private void excuteListVouchers() {
        output.printVouchers(voucherService.findVouchers());
    }
}
