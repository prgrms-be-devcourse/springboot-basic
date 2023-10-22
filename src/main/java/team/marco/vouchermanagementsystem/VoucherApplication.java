package team.marco.vouchermanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.vouchermanagementsystem.service.BlacklistService;
import team.marco.vouchermanagementsystem.service.VoucherService;
import team.marco.vouchermanagementsystem.util.Console;

@Component
public class VoucherApplication {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    private final VoucherService voucherService;
    private final BlacklistService blacklistService;

    private Boolean isRunning;

    public VoucherApplication(VoucherService service, BlacklistService blacklistService) {
        this.voucherService = service;
        this.blacklistService = blacklistService;
        this.isRunning = true;
    }

    public void run() {
        while (isRunning) {
            try {
                selectCommand();
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    public void selectCommand() {
        Console.print("""
                === 쿠폰 관리 프로그램 ===
                exit: 프로그램 종료
                create: 쿠폰 생성
                list: 쿠폰 목록 조회
                blacklist: 블랙 리스트 유저 조회""");

        String input = Console.readString();

        CommandType commandType = CommandType.getCommandType(input);
        switch (commandType) {
            case CREATE -> createVoucher();
            case LIST -> getVoucherList();
            case BLACKLIST -> getBlacklist();
            case EXIT -> isRunning = false;
        }
    }

    private void createVoucher() {
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

        Console.printList(voucherService.getVouchersInfo());
    }

    private void getBlacklist() {
        logger.info("Call getBlackListUsers()");

        Console.printList(blacklistService.getBlacklist());
    }

    private void close() {
        logger.info("Call close()");

        Console.print("프로그램이 종료되었습니다.");
    }

    private void handleException(Exception e) {
        if(e instanceof NumberFormatException) {
            Console.print("숫자를 입력해 주세요.");
            return;
        }

        if(e instanceof IllegalArgumentException) {
            Console.print(e.getMessage());
            return;
        }

        logger.error(e.toString());

        Console.print("프로그램에 에러가 발생했습니다.");
        isRunning = false;
        close();
    }
}
