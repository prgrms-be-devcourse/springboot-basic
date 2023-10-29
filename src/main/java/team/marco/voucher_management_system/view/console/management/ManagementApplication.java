package team.marco.voucher_management_system.view.console.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.CustomerController;
import team.marco.voucher_management_system.controller.VoucherController;
import team.marco.voucher_management_system.view.console.ConsoleUtil;

import java.io.UncheckedIOException;

@Component
public class ManagementApplication {
    private static final Logger logger = LoggerFactory.getLogger(ManagementApplication.class);

    private final VoucherController voucherController;
    private final CustomerController userController;

    private Boolean isRunning;

    public ManagementApplication(VoucherController voucherController, CustomerController userController) {
        this.voucherController = voucherController;
        this.userController = userController;
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
        ConsoleUtil.print("=== 관리자 페이지 ===");

        for(ManagementCommandType type : ManagementCommandType.values()) {
            ConsoleUtil.print(type.getInfo());
        }

        ConsoleUtil.println();

        ConsoleUtil.print("Q. 이용하실 서비스를 선택해 주세요.(숫자)");
        int input = ConsoleUtil.readInt();

        ManagementCommandType commandType = ManagementCommandType.get(input);
        switch (commandType) {
            case CREATE_VOUCHER -> createVoucher();
            case VOUCHER_LIST -> getVoucherList();
            case SEARCH_VOUCHER -> getVoucherInfo();
            case CUSTOMER_LIST -> getCustomerList();
            case BLACKLIST -> getBlacklist();
            case BACK -> close();
        }
    }

    private void getCustomerList() {
        /**
         * TODO: getCustomerList() 구현
         */
    }

    private void getVoucherInfo() {
        ConsoleUtil.print("쿠폰 번호를 입력해 주세요.");
        String voucherId = ConsoleUtil.readString();

        ConsoleUtil.print(voucherController.getVoucherInfo(voucherId));
    }

    private void createVoucher() {
        logger.info("Call createVoucher()");

        ConsoleUtil.print("""
                0: 고정 금액 할인 쿠폰
                1: % 할인 쿠폰""");

        int selected = ConsoleUtil.readInt();

        switch (selected) {
            case 0 -> createFixedAmountVoucher();
            case 1 -> createPercentDiscountVoucher();
            default -> throw new IllegalArgumentException("올바르지 않은 입력입니다.");
        }
    }

    private void createPercentDiscountVoucher() {
        logger.info("Call createPercentDiscountVoucher()");

        ConsoleUtil.print("할인율을 입력해 주세요.");
        int percent = ConsoleUtil.readInt();

        voucherController.createPercentDiscountVoucher(percent);
    }

    private void createFixedAmountVoucher() {
        logger.info("Call createFixedAmountVoucher()");

        ConsoleUtil.print("할인 금액을 입력해 주세요.");
        int amount = ConsoleUtil.readInt();

        voucherController.createFixedAmountVoucher(amount);
    }

    private void getVoucherList() {
        logger.info("Call getVoucherList()");

        ConsoleUtil.printStringList(voucherController.getVouchersInfo());
    }

    private void getBlacklist() {
        logger.info("Call getBlackListUsers()");

        ConsoleUtil.printStringList(userController.getBlacklistInfo());
    }

    private void close() {
        isRunning = false;
    }

    private void handleException(Exception e) {
        if(e instanceof NumberFormatException) {
            ConsoleUtil.print("숫자를 입력해 주세요.");
            return;
        }

        if(e instanceof IllegalArgumentException) {
            ConsoleUtil.print(e.getMessage());
            return;
        }

        logger.error(e.toString());

        String errorMessage = (e instanceof UncheckedIOException)? "파일을 처리하는 과정에서 에러가 발생했습니다." : "프로그램에 에러가 발생했습니다.";
        ConsoleUtil.print(errorMessage);

        isRunning = false;

        close();
    }
}
