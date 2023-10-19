package team.marco.vouchermanagementsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.vouchermanagementsystem.service.VoucherService;
import team.marco.vouchermanagementsystem.util.Console;

import java.io.BufferedReader;

@Component
public class VoucherApplication {
    private static final Logger logger  = LoggerFactory.getLogger(VoucherApplication.class);
    private static final String INFO_DELIMINATOR = "\n";

    private final VoucherService voucherService;

    public VoucherApplication(VoucherService service) {
        this.voucherService = service;
    }

    public void run() {
        try {
            runCommand();
        } catch (Exception e) {
            logger.error(e.toString());
            Console.print("프로그램에 에러가 발생했습니다.");
        }

        close();
    }

    public void runCommand() {
        Console.print("""
                === 쿠폰 관리 프로그램 ===
                exit: 프로그램 종료
                create: 쿠폰 생성
                list: 쿠폰 목록 조회
                blacklist: 블랙 리스트 유저 조회""");

        String input = Console.readString();

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
        } catch (NumberFormatException e) {
            Console.print("숫자를 입력해 주세요.");
        } catch (IllegalArgumentException e) {
            Console.print(e.getMessage());
        }

        runCommand();
    }

    private void getBlacklistUsers() {
        logger.info("Call getBlackListUsers()");

        String blacklistUsers = String.join(INFO_DELIMINATOR, voucherService.getBlacklistUsers());
        Console.print(blacklistUsers);
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

        int percent = Console.readInt("할인율을 입력해 주세요.");
        voucherService.createPercentDiscountVoucher(percent);
    }

    private void createFixedAmountVoucher() {
        logger.info("Call createFixedAmountVoucher()");

        int amount = Console.readInt("할인 금액을 입력해 주세요.");
        voucherService.createFixedAmountVoucher(amount);
    }

    private void getVoucherList() {
        logger.info("Call getVoucherList()");

        String vouchersInfo = String.join(INFO_DELIMINATOR, voucherService.getVouchersInfo());

        if (!vouchersInfo.isBlank()) {
            Console.print(vouchersInfo);
        }

        Console.print("조회가 완료되었습니다.");
    }

    private void close() {
        logger.info("Call close()");
        Console.print("프로그램이 종료되었습니다.");
    }
}
