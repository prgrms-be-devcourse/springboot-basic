package team.marco.voucher_management_system.view.consoleapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.view.consoleapp.management.ManagementApplication;
import team.marco.voucher_management_system.view.consoleapp.wallet.WalletApplication;

import java.io.UncheckedIOException;

@Component
public class VoucherApplication {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    private final WalletApplication walletApplication;
    private final ManagementApplication managementApplication;

    private Boolean isRunning;

    public VoucherApplication(WalletApplication walletApplication, ManagementApplication managementApplication) {
        this.walletApplication = walletApplication;
        this.managementApplication = managementApplication;
        this.isRunning = true;
    }

    public void run() {
        while (isRunning) {
            try {
                selectService();
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    public void selectService() {
        ConsoleUtil.print("=== 메인 페이지 ===");

        for(ServiceType type : ServiceType.values()) {
            ConsoleUtil.print(type.getInfo());
        }

        ConsoleUtil.println();

        ConsoleUtil.print("Q. 이용하실 서비스를 선택해 주세요. (숫자)");
        int input = ConsoleUtil.readInt();

        ServiceType type = ServiceType.get(input);
        switch (type) {
            case MANAGEMENT -> runManagementApplication();
            case WALLET -> runWalletApplication();
            case EXIT -> close();
        }
    }

    private void runWalletApplication() {
        walletApplication.run();
    }
    private void runManagementApplication() {
        managementApplication.run();
    }

    private void close() {
        logger.info("Call close()");

        isRunning = false;
        ConsoleUtil.print("프로그램이 종료되었습니다.");
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
