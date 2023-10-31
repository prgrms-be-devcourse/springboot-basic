package team.marco.voucher_management_system.view.consoleapp.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.customer.CustomerController;
import team.marco.voucher_management_system.controller.voucher.VoucherController;
import team.marco.voucher_management_system.view.consoleapp.ConsoleUtil;

@Component
public class WalletApplication {
    private static final Logger logger = LoggerFactory.getLogger(WalletApplication.class);

    private final VoucherController voucherController;
    private final CustomerController customerController;

    private Boolean isRunning;

    public WalletApplication(VoucherController voucherController, CustomerController customerController) {
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.isRunning = true;
    }

    public void run() {
        ConsoleUtil.print("지갑 서비스를 이용하기 위해 사용자의 아이디를 입력해주세요.");
        String customerId = ConsoleUtil.readString();

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
        ConsoleUtil.print("=== 지갑 페이지 ===");

        for(WalletCommandType type : WalletCommandType.values()) {
            ConsoleUtil.print(type.getInfo());
        }

        ConsoleUtil.println();

        ConsoleUtil.print("Q. 이용하실 서비스를 선택해 주세요.(숫자)");
        int input = ConsoleUtil.readInt();

        WalletCommandType commandType = WalletCommandType.get(input);
        switch (commandType) {
            case REGISTER -> registerVoucher(customerId);
            case LIST -> getMyVouchers(customerId);
            case REMOVE -> removeVoucher();
            case BACK -> backToMainApplication();
        }
    }

    private void registerVoucher(String customerId) {
        ConsoleUtil.print("등록하실 쿠폰 번호를 입력해주세요.");
        String voucherId = ConsoleUtil.readString();
        voucherController.assignVoucherOwner(voucherId, customerId);

        ConsoleUtil.print("쿠폰 등록이 완료되었습니다.");
    }

    private void getMyVouchers(String customerId) {
        ConsoleUtil.printStringList(voucherController.getVouchersInfo(customerId));
    }

    private void removeVoucher() {
        ConsoleUtil.print("제거할 쿠폰 번호를 입력해주세요.");
        String voucherId = ConsoleUtil.readString();
        voucherController.deleteVoucher(voucherId);

        ConsoleUtil.print("쿠폰 삭제가 완료되었습니다.");
    }

    private void backToMainApplication() {
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

        ConsoleUtil.print(e.getMessage());

        isRunning = false;
    }

}
