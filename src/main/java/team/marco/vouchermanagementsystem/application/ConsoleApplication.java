package team.marco.vouchermanagementsystem.application;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.vouchermanagementsystem.service.BlacklistService;
import team.marco.vouchermanagementsystem.service.VoucherService;
import team.marco.vouchermanagementsystem.util.Console;

@Component
public class ConsoleApplication {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);
    private static final String INFO_DELIMINATOR = "\n";

    private final VoucherService voucherService;
    private final BlacklistService blacklistService;

    private boolean runningFlag;

    public ConsoleApplication(VoucherService service, BlacklistService blacklistService) {
        this.voucherService = service;
        this.blacklistService = blacklistService;
    }

    public void run() {
        runningFlag = true;

        try {
            selectCommand();
        } catch (Exception e) {
            logger.error(e.toString());
            Console.print("프로그램에 에러가 발생했습니다.");
        }

        close();
    }

    public void selectCommand() {
        while (runningFlag) {
            Console.print("""
                    === 쿠폰 관리 프로그램 ===
                    exit: 프로그램 종료
                    create: 쿠폰 생성
                    list: 쿠폰 목록 조회
                    blacklist: 블랙 리스트 유저 조회""");

            String input = Console.readString();

            executeCommand(input);
        }
    }

    private void executeCommand(String input) {
        try {
            CommandType commandType = CommandType.getCommandType(input);

            switchCommand(commandType);
        } catch (NumberFormatException e) {
            Console.print("숫자를 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            Console.print(e.getMessage());
        }
    }

    private void switchCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE -> selectVoucher();
            case LIST -> getVoucherList();
            case BLACKLIST -> getBlacklist();
            case EXIT -> runningFlag = false;
        }
    }

    private void selectVoucher() {
        logger.info("Call createVoucher()");

        Console.print("""
                0: 고정 금액 할인 쿠폰
                1: % 할인 쿠폰""");

        int selected = Console.readInt();

        switch (selected) {
            case 0 -> createFixedAmountVoucher();
            case 1 -> createPercentDiscountVoucher();
            default -> throw new IllegalArgumentException("올바르지 않은 입력입니다.");
        }
    }

    private void createPercentDiscountVoucher() {
        logger.info("Call createPercentDiscountVoucher()");

        Console.print("할인율을 입력해 주세요.");

        int percent = Console.readInt();

        voucherService.createPercentDiscountVoucher(percent);
    }

    private void createFixedAmountVoucher() {
        logger.info("Call createFixedAmountVoucher()");

        Console.print("할인 금액을 입력해 주세요.");

        int amount = Console.readInt();

        voucherService.createFixedAmountVoucher(amount);
    }

    private void getVoucherList() {
        logger.info("Call getVoucherList()");

        printInquireResult(voucherService.getVouchersInfo());
    }

    private void getBlacklist() {
        logger.info("Call getBlackListUsers()");

        printInquireResult(blacklistService.getBlacklist());
    }

    private void printInquireResult(List<String> inquiredList) {
        String joinedString = String.join(INFO_DELIMINATOR, inquiredList);

        if (!joinedString.isBlank()) {
            Console.print(joinedString);
        }

        Console.print("조회가 완료되었습니다.");
    }

    private void close() {
        logger.info("Call close()");

        Console.print("프로그램이 종료되었습니다.");
    }
}
