package team.marco.vouchermanagementsystem;

import org.springframework.stereotype.Component;
import team.marco.vouchermanagementsystem.service.VoucherService;
import team.marco.vouchermanagementsystem.util.Console;

import java.util.stream.Collectors;

@Component
public class VoucherApplication {
    private static final String INFO_DELIMINATOR  = "\n";
    private final Console console;
    private final VoucherService voucherService;

    public VoucherApplication(Console console, VoucherService service) {
        this.console = console;
        this.voucherService = service;
    }

    public void run() {
        console.printCommandManual();
        String input = console.readString();

        try {
            CommandType commandType = CommandType.getCommandType(input);
            runCommand(commandType);
        } catch (Exception e) {
            console.printError(e);
            run();
        }
    }

    public void runCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE -> createVoucher();
            case LIST -> getVoucherList();
            case EXIT -> {
                close();

                return;
            }
        }

        run();
    }

    private void createVoucher() {
        console.printVoucherTypes();
        int selected = console.readInt();

        switch (selected) {
            case 0 -> createFixedAmountVoucher();
            case 1 -> createPercentDiscountVoucher();
            default -> throw new RuntimeException("올바르지 않은 입력입니다.");
        }
    }

    private void createPercentDiscountVoucher() {
        int percent = console.readInt("할인율을 입력해 주세요.");
        voucherService.createPercentDiscountVoucher(percent);
    }

    private void createFixedAmountVoucher() {
        int amount = console.readInt("할인 금액을 입력해 주세요.");
        voucherService.createFixedAmountVoucher(amount);
    }

    private void getVoucherList() {
        String vouchersInfo = String.join(INFO_DELIMINATOR, voucherService.getVouchersInfo());
        console.print(vouchersInfo);
    }

    private void close() {
        // TODO: 데이터 저장 기능
        System.out.println("exit program...");
    }
}
