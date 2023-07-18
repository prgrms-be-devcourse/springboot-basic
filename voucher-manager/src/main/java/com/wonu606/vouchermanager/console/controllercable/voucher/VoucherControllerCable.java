package com.wonu606.vouchermanager.console.controllercable.voucher;

import com.wonu606.vouchermanager.console.controllercable.voucher.io.VoucherConsoleIo;
import com.wonu606.vouchermanager.controller.VoucherController;
import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWalletDto;
import com.wonu606.vouchermanager.domain.customer.Customer;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class VoucherControllerCable {

    private final VoucherConsoleIo consoleIo;
    private final VoucherController controller;

    public VoucherControllerCable(VoucherConsoleIo consoleIo,
            VoucherController controller) {
        this.consoleIo = consoleIo;
        this.controller = controller;
    }

    public void run() {
        VoucherControllerMenu menu = VoucherControllerMenu.START;
        while (menu.isNotExit()) {
            try {
                menu = consoleIo.selectMenu();
                executeMenuAction(menu);
            } catch (Exception exception) {
                consoleIo.displayMessage(exception.getMessage());
            }
        }
    }

    private void executeMenuAction(VoucherControllerMenu menu) {
        switch (menu) {
            case EXIT:
                return;

            case LIST:
                List<VoucherResponse> voucherResponses = controller.getVoucherList();
                consoleIo.displayVoucherList(voucherResponses);
                return;

            case CREATE:
                VoucherCreateRequest voucherCreateRequest = createVoucherCreateRequest();
                controller.createVoucher(voucherCreateRequest);
                return;
            case ASSIGN:
                VoucherWalletCreateRequest voucherWalletCreateRequest = createVoucherWalletCreateRequest();
                controller.assignWallet(voucherWalletCreateRequest);
                return;

            case CUSTOMER_LIST:
                String voucherId = consoleIo.readString("VoucherId");
                List<CustomerResponse> customerResponses = controller.getCustomersOwnedByVoucherId(voucherId);
                consoleIo.displayCustomerList(customerResponses);
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private VoucherWalletCreateRequest createVoucherWalletCreateRequest() {
        String uuid = consoleIo.readString("Voucher UUID");
        String emailAddress = consoleIo.readString("Customer EmailAddress");
        return new VoucherWalletCreateRequest(uuid, emailAddress);
    }

    private VoucherCreateRequest createVoucherCreateRequest() {
        String type = consoleIo.selectVoucherType();
        double discountValue = consoleIo.readDouble("discount");
        return new VoucherCreateRequest(type, discountValue);
    }
}
