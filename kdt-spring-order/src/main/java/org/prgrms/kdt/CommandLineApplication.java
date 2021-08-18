package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineApplication {
    private static final String EXIT = "exit";
    private static final String LIST = "list";
    private static final String CREATE = "create";
    private static BufferedReader br;
    private static VoucherService voucherService;

    public static void main(String[] args) throws IOException {
        CommandLineApplication app = new CommandLineApplication();

        // initialize BufferedReader, VoucherService
        app.init();

        String cmd = "";

        while (true) {
            app.printInitMenu();
            cmd = br.readLine().toLowerCase().trim();

            // 명령어 유효성 검사
            if (app.isNotValidCommand(cmd)) {
                System.out.println("Not valid command, Try again");
                continue;
            }

            // 종료
            if (cmd.equals(EXIT))
                return;

            app.executeCommand(cmd);
        }
    }

    private static void init() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        br = new BufferedReader(new InputStreamReader(System.in));
        voucherService = applicationContext.getBean(VoucherService.class);

    }

    private void executeCommand(String cmd) throws IOException {
        if (cmd.equals(CREATE)) {
            createVoucher();
        } else {
            printVouchers();
        }
    }

    private void printVouchers() {
        voucherService.vouchers().stream()
                .forEach(System.out::println);
    }

    private void createVoucher() throws IOException {
        printCreateMenue();
        while (true) {
            String[] line = br.readLine().trim().split(" ");
            if (isNotValidVoucherTypeAmount(line)) {
                System.out.println("Not valid input, Try again");
                continue;
            }
            voucherService.save(new RequestCreatVoucherDto(Integer.parseInt(line[0]), Long.parseLong(line[1])));
            System.out.println("Successfully creat voucher");
            break;
        }
    }

    private boolean isNotValidVoucherTypeAmount(String[] line) {
        if (line.length != 2)
            return true;

        for (String str : line) {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i)))
                    return true;
            }
        }

        int type = Integer.parseInt(line[0]);
        long amount = Long.parseLong(line[1]);

        if (type != 0 && type != 1)
            return true;

        if (amount < 0)
            return true;

        return false;
    }

    private void printCreateMenue() {
        System.out.println("=== Create Voucher ==");
        System.out.println("Enter the type of voucher");
        System.out.println("Enter the amount of discount");
        System.out.println("FixedAmountVoucher: 0, PercentAmountVoucher: 1");
        System.out.println("Please separate the type and amount with a space");
        System.out.println("Example: 0 10");
        System.out.println("type: FixedAmountVoucher, amount: 10");
        System.out.println("=====================");
    }

    private boolean isNotValidCommand(String line) {
        return !line.equals(EXIT) && !line.equals(LIST) && !line.equals(CREATE);
    }

    private void printInitMenu() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type List to list all vouchers");
    }
}
