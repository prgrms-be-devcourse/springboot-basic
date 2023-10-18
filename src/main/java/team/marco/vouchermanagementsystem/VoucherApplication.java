package team.marco.vouchermanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.vouchermanagementsystem.service.VoucherService;
import team.marco.vouchermanagementsystem.util.Console;

@Component
public class VoucherApplication {
    private static final Logger logger  = LoggerFactory.getLogger(VoucherApplication.class);
    private static final String INFO_DELIMINATOR = "\n";

    private final Console console;
    private final VoucherService voucherService;

    public VoucherApplication(Console console, VoucherService service) {
        this.console = console;
        this.voucherService = service;
    }

    public void run() {
        try {
            runCommand();
        } catch (Exception e) {
            logger.error(e.toString());
            console.print("프로그램에 에러가 발생했습니다.");
        }

        close();
    }

    public void runCommand() {
        console.printCommandManual();
        String input = console.readString();

        try {
            CommandType commandType = CommandType.getCommandType(input);

            switch (commandType) {
                case CREATE -> createVoucher();
                case LIST -> getVoucherList();
                case BLACKLIST -> getBlacklistUsers();
                case EXIT -> {
                    return;
                }
            }
        } catch (IllegalArgumentException e) {
            console.printError(e);
            e.printStackTrace();
        }

        runCommand();
    }

    private void getBlacklistUsers() {
        logger.info("Call getBlackListUsers()");

        String blacklistUsers = String.join(INFO_DELIMINATOR, voucherService.getBlacklistUsers());
        console.print(blacklistUsers);
    }

    private void createVoucher() {
        logger.info("Call createVoucher()");

        console.printVoucherTypes();
        int selected = console.readInt();

        switch (selected) {
            case 0 -> createFixedAmountVoucher();
            case 1 -> createPercentDiscountVoucher();
            default -> throw new RuntimeException("올바르지 않은 입력입니다.");
        }
    }

    private void createPercentDiscountVoucher() {
        logger.info("Call createPercentDiscountVoucher()");

        int percent = console.readInt("할인율을 입력해 주세요.");
        voucherService.createPercentDiscountVoucher(percent);
    }

    private void createFixedAmountVoucher() {
        logger.info("Call createFixedAmountVoucher()");

        int amount = console.readInt("할인 금액을 입력해 주세요.");
        voucherService.createFixedAmountVoucher(amount);
    }

    private void getVoucherList() {
        logger.info("Call getVoucherList()");

        String vouchersInfo = String.join(INFO_DELIMINATOR, voucherService.getVouchersInfo());
        console.print(vouchersInfo);
    }

    private void close() {
        logger.info("Call close()");
        console.print("프로그램이 종료되었습니다.");
    }
}
