package team.marco.vouchermanagementsystem.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.vouchermanagementsystem.controller.ConsoleBlacklistController;
import team.marco.vouchermanagementsystem.controller.ConsoleCustomerController;
import team.marco.vouchermanagementsystem.controller.ConsoleVoucherController;
import team.marco.vouchermanagementsystem.util.Console;

@Component
public class ConsoleApplication {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);

    private final ConsoleVoucherController voucherController;
    private final ConsoleBlacklistController blacklistController;
    private final ConsoleCustomerController customerController;

    private boolean runningFlag;

    public ConsoleApplication(ConsoleVoucherController voucherController,
                              ConsoleBlacklistController blacklistController,
                              ConsoleCustomerController customerController) {
        this.voucherController = voucherController;
        this.blacklistController = blacklistController;
        this.customerController = customerController;
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
                    blacklist: 블랙 리스트 유저 조회
                    customer: 고객 관리 메뉴""");

            String input = Console.readString();

            executeCommand(input);
        }
    }

    private void executeCommand(String input) {
        try {
            CommandType commandType = CommandType.getCommandType(input);

            switchCommand(commandType);
        } catch (NumberFormatException e) {
            logger.warn(e.toString());
            Console.print("숫자를 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            logger.warn(e.toString());
            Console.print(e.getMessage());
        }
    }

    private void switchCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE -> voucherController.selectVoucher();
            case LIST -> voucherController.voucherList();
            case BLACKLIST -> blacklistController.blacklist();
            case CUSTOMER -> customerController.run();
            case EXIT -> runningFlag = false;
        }
    }

    private void close() {
        Console.print("프로그램이 종료되었습니다.");
    }
}
