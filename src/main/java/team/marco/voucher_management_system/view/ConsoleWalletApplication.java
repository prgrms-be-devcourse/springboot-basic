package team.marco.voucher_management_system.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.CustomerController;
import team.marco.voucher_management_system.controller.VoucherController;
import team.marco.voucher_management_system.view.util.Console;

@Component
public class ConsoleWalletApplication {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleWalletApplication.class);

    private final VoucherController voucherController;
    private final CustomerController customerController;

    private Boolean isRunning;

    public ConsoleWalletApplication(VoucherController voucherController,  CustomerController customerController) {
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.isRunning = true;
    }

    public void run() {
        Console.print("지갑 서비스를 이용하기 위해 사용자의 아이디를 입력해주세요.");
        String customerId = Console.readString();

        if(!customerController.isExistCustomer(customerId)) {
            throw new IllegalArgumentException("올바른 사용자 아이디가 아닙니다.");
        }

        while (isRunning) {
            try {
                selectCommand(customerId);
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    private void selectCommand(String customerId) {
        Console.print("""
                === 지갑 관리 프로그램 ===
                register: 쿠폰 등록
                list: 나의 쿠폰 목록 조회
                remove: 쿠폰 삭제
                back: 쿠폰 관리 페이지로 돌아가기""");

        String input = Console.readString();

        CommandType commandType = CommandType.getCommandType(input);
        switch (commandType) {
            case REGISTER -> registerVoucher(customerId);
            case LIST -> getMyVouchers(customerId);
            case REMOVE -> removeVoucher(customerId);
            case BACK -> backToMainApplication();
        }
    }

    private void registerVoucher(String customerId) {
        Console.print("등록하실 쿠폰 번호를 입력해주세요.");
        String voucherId = Console.readString();
        voucherController.assignVoucherOwner(voucherId, customerId);

        Console.print("쿠폰 등록이 완료되었습니다.");
    }

    private void getMyVouchers(String customerId) {
        Console.printStringList(voucherController.getVouchersInfo(customerId));
    }

    private void removeVoucher(String customerId) {
        Console.print("제거할 쿠폰 번호를 입력해주세요.");
        String voucherId = Console.readString();
        voucherController.deleteVoucher(voucherId);

        Console.print("쿠폰 삭제가 완료되었습니다.");
    }

    private void backToMainApplication() {
        isRunning = false;
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

        Console.print(e.getMessage());

        isRunning = false;
    }

}
