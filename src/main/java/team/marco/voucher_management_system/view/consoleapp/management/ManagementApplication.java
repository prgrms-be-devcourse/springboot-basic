package team.marco.voucher_management_system.view.consoleapp.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.customer.CustomerController;
import team.marco.voucher_management_system.controller.voucher.VoucherController;
import team.marco.voucher_management_system.controller.voucher.VoucherCreateRequest;

import java.io.UncheckedIOException;

import static team.marco.voucher_management_system.domain.voucher.VoucherType.FIXED;
import static team.marco.voucher_management_system.domain.voucher.VoucherType.PERCENT;
import static team.marco.voucher_management_system.view.consoleapp.ConsoleUtil.*;

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
        print("=== 관리자 페이지 ===");

        for(ManagementCommandType type : ManagementCommandType.values()) {
            print(type.getInfo());
        }

        println();

        print("Q. 이용하실 서비스를 선택해 주세요.(숫자)");
        int input = readInt();

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
        print("쿠폰 번호를 입력해 주세요.");
        String voucherId = readString();

        print(voucherController.getVoucher(voucherId));
    }

    private void createVoucher() {
        logger.info("Call createVoucher()");

        print("""
                0: 고정 금액 할인 쿠폰
                1: % 할인 쿠폰""");

        int selected = readInt();

        switch (selected) {
            case 0 -> createFixedAmountVoucher();
            case 1 -> createPercentDiscountVoucher();
            default -> throw new IllegalArgumentException("올바르지 않은 입력입니다.");
        }
    }

    private void createPercentDiscountVoucher() {
        logger.info("Call createPercentDiscountVoucher()");

        print("할인율을 입력해 주세요.");
        int percent = readInt();

        VoucherCreateRequest request = new VoucherCreateRequest(PERCENT, percent);
        voucherController.createVoucher(request);

        println("쿠폰 생성이 완료되었습니다.");
    }

    private void createFixedAmountVoucher() {
        logger.info("Call createFixedAmountVoucher()");

        print("할인 금액을 입력해 주세요.");
        int amount = readInt();

        VoucherCreateRequest request = new VoucherCreateRequest(FIXED, amount);
        voucherController.createVoucher(request);

        println("쿠폰 생성이 완료되었습니다.");
    }

    private void getVoucherList() {
        logger.info("Call getVoucherList()");

        printVoucherList(voucherController.getVouchers());
        println();
        println("조회가 완료되었습니다.");
    }

    private void getBlacklist() {
        logger.info("Call getBlackListUsers()");

        printStringList(userController.getBlacklistInfo());
    }

    private void close() {
        isRunning = false;
    }

    private void handleException(Exception e) {
        if(e instanceof NumberFormatException) {
            print("숫자를 입력해 주세요.");
            return;
        }

        if(e instanceof IllegalArgumentException) {
            print(e.getMessage());
            return;
        }

        logger.error(e.toString());

        String errorMessage = (e instanceof UncheckedIOException)? "파일을 처리하는 과정에서 에러가 발생했습니다." : "프로그램에 에러가 발생했습니다.";
        print(errorMessage);

        isRunning = false;

        close();
    }
}
