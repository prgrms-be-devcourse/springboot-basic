package prgms.springbasic;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.springbasic.console.Printer;
import prgms.springbasic.voucher.Voucher;
import prgms.springbasic.voucher.VoucherService;
import prgms.springbasic.voucher.VoucherType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class CommandLineApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CommandLineAppConfig.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Printer printer = new Printer();

        while (true) {
            printer.printCommandList();
            String inputCommand = reader.readLine();

            if (inputCommand.equals("create")) {
                printer.printVoucherTypeList();
                int voucherNumber = Integer.parseInt(reader.readLine());

                if (voucherNumber == 1) {
                    printer.printAmountInputInfo();
                    String amount = reader.readLine();
                    Voucher newVoucher = voucherService.createVoucher(VoucherType.FIXED_AMOUNT_VOUCHER, UUID.randomUUID(), amount);
                    printer.printVoucherCreateSuccess(newVoucher);
                } else if (voucherNumber == 2) {
                    printer.printPercentInputInfo();
                    String percent = reader.readLine();
                    Voucher newVoucher = voucherService.createVoucher(VoucherType.PERCENT_DISCOUNT_VOUCHER, UUID.randomUUID(), percent);
                    printer.printVoucherCreateSuccess(newVoucher);
                }

            } else if (inputCommand.equals("list")) {
                if (voucherService.getVoucherList().isEmpty()) {
                    printer.printVoucherListEmpty();
                    continue;
                }
                printer.printVoucherList(voucherService.getVoucherList());

            } else if (inputCommand.equals("exit")) {
                break;
            }
        }
    }
}
