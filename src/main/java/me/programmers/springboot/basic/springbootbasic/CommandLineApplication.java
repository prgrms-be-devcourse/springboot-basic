package me.programmers.springboot.basic.springbootbasic;

import me.programmers.springboot.basic.springbootbasic.io.Console;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleInput;
import me.programmers.springboot.basic.springbootbasic.io.ConsoleOutput;
import me.programmers.springboot.basic.springbootbasic.voucher.VoucherStatus;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.UUID;

public class CommandLineApplication {


    public static void main(String[] args) {
        new CommandLineApplication().run();
    }

    public void run() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        VoucherService voucherService = context.getBean(VoucherService.class);
        Console console = new Console();

        boolean isExit = false;
        while (!isExit) {
            showMenu(console);
            String cmd = inputCommand(console);
            CommandStatus command = CommandStatus.getCommand(cmd);
            switch (command) {
                case EXIT:
                    isExit = true;
                    break;
                case CREATE:
                    createVoucher(console, console, voucherService);
                    break;
                case LIST:
                    showPresentVoucherList(console, voucherService);
                    break;
                default:
                    break;
            }
            console.output("\n");
        }
    }

    private void showPresentVoucherList(ConsoleOutput output, VoucherService voucherService) {
        List<Voucher> vouchers = voucherService.getVouchers();
        output.output("바우처 리스트");
        for (Voucher voucher : vouchers) {
            output.output(voucher.toString());
        }
    }

    private void createVoucher(ConsoleInput input, ConsoleOutput output, VoucherService voucherService) {
        String cmd = input.inputCommand("Type fixed or percent: ");

        VoucherStatus voucherStatus = VoucherStatus.getVoucherStatus(cmd);
        switch (voucherStatus) {
            case FIXED:
                voucherService.save(new FixedAmountVoucher(UUID.randomUUID(), 100));
                break;
            case PERCENT:
                voucherService.save(new PercentAmountVoucher(UUID.randomUUID(), 50));
                break;
            default:
                break;
        }
    }

    private String inputCommand(ConsoleInput console) {
        return console.inputCommand("명령어 입력: ");
    }

    private void showMenu(ConsoleOutput out) {
        out.output("=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.");
    }
}
