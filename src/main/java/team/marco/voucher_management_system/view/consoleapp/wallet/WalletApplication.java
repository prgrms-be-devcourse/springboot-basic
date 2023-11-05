package team.marco.voucher_management_system.view.consoleapp.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.controller.customer.CustomerController;
import team.marco.voucher_management_system.controller.voucher.VoucherController;
import team.marco.voucher_management_system.domain.customer.Customer;
import team.marco.voucher_management_system.view.consoleapp.ConsoleUtil;

import java.util.UUID;

import static team.marco.voucher_management_system.error.ErrorMessage.CUSTOMER_ID_INVALID;
import static team.marco.voucher_management_system.error.ErrorMessage.NUMBER_REQUIRED;
import static team.marco.voucher_management_system.view.consoleapp.ConsoleUtil.*;

@Component
public class WalletApplication {
    private static final Logger logger = LoggerFactory.getLogger(WalletApplication.class);
    public static final String  CUSTOMER_EMAIL_REQUEST = "사용자 이메일을 입력해주세요.";
    public static final String WALLET_HEADER = "==== 지갑 페이지 ====";
    public static final String SELECT_SERVICE = "Q. 이용하실 서비스를 선택해 주세요.";
    public static final String VOUCHER_ID_REQUEST = "쿠폰 번호를 입력해 주세요.";
    public static final String  VOUCHER_DELETE_COMPLETE = "쿠폰 삭제가 완료되었습니다.";

    private final VoucherController voucherController;
    private final CustomerController customerController;

    private Boolean isRunning;
    private UUID customerId;

    public WalletApplication(VoucherController voucherController, CustomerController customerController) {
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.isRunning = true;
    }

    public void run() {
        customerId = validateCustomer();

        while (isRunning) {
            try {
                provideCommandManual();
                WalletCommandType input = getCommandType();
                handleCommand(input);
            } catch (IllegalArgumentException e) {
                handleException(e);
            }
        }
    }

    private UUID validateCustomer() {
        ConsoleUtil.print(CUSTOMER_EMAIL_REQUEST);
        String customerEmail = ConsoleUtil.readString();

        Customer customer = customerController.findCustomerByEmail(customerEmail);

        if(customer == null) {
            throw new IllegalArgumentException(CUSTOMER_ID_INVALID);
        }
        return customer.getId();
    }

    public void provideCommandManual() {
        print(WALLET_HEADER);

        for(WalletCommandType type : WalletCommandType.values()) {
            print(type.getManual());
        }

        println();
    }

    public WalletCommandType getCommandType() {
        print(SELECT_SERVICE);

        return WalletCommandType.get(readInt());
    }

    public void handleCommand(WalletCommandType commandType) {
        switch (commandType) {
            case REGISTER -> registerVoucher(customerId);
            case LIST -> getMyVouchers(customerId);
            case REMOVE -> removeVoucher();
            case BACK -> backToMainApplication();
        }
    }

    private void getMyVouchers(UUID customerId) {
        // TODO
    }

    private void registerVoucher(UUID customerId) {
        // TODO
    }

    private void removeVoucher() {
        ConsoleUtil.print(VOUCHER_ID_REQUEST);
        String voucherId = ConsoleUtil.readString();
        voucherController.deleteVoucher(voucherId);

        ConsoleUtil.print(VOUCHER_DELETE_COMPLETE);
    }

    private void backToMainApplication() {
        isRunning = false;
    }

    private void handleException(Exception e) {
        if(e instanceof NumberFormatException) {
            ConsoleUtil.print(NUMBER_REQUIRED);
            return;
        }

        ConsoleUtil.println(e.getMessage());
    }
}
