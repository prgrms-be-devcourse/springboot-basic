package com.wonu606.vouchermanager.console.controllercable.voucher;

import com.wonu606.vouchermanager.console.controllercable.voucher.io.VoucherConsoleIo;
import com.wonu606.vouchermanager.controller.voucher.VoucherController;
import com.wonu606.vouchermanager.controller.voucher.reqeust.VoucherCreateRequest;
import com.wonu606.vouchermanager.controller.voucher.reqeust.OwnedCustomersRequest;
import com.wonu606.vouchermanager.controller.voucher.reqeust.WalletAssignRequest;
import com.wonu606.vouchermanager.controller.voucher.response.OwnedCustomersResponse;
import com.wonu606.vouchermanager.controller.voucher.response.VoucherResponse;
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
                VoucherResponse voucherResponse = controller.getVoucherList();
                consoleIo.displayVoucherList(voucherResponse);
                return;

            case CREATE:
                VoucherCreateRequest voucherCreateRequest = createVoucherCreateRequest();
                controller.createVoucher(voucherCreateRequest);
                return;
            case ASSIGN:
                WalletAssignRequest voucherWalletCreateRequest = createVoucherWalletCreateRequest();
                controller.assignWallet(voucherWalletCreateRequest);
                return;

            case CUSTOMER_LIST:
                String voucherId = consoleIo.readString("VoucherId");
                OwnedCustomersResponse ownedCustomersResponse = controller.getOwnedCustomersByVoucher(
                        new OwnedCustomersRequest(voucherId));
                consoleIo.displayCustomerList(ownedCustomersResponse);
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private WalletAssignRequest createVoucherWalletCreateRequest() {
        String uuid = consoleIo.readString("Voucher UUID");
        String emailAddress = consoleIo.readString("Customer EmailAddress");
        return new WalletAssignRequest(uuid, emailAddress);
    }

    private VoucherCreateRequest createVoucherCreateRequest() {
        String type = consoleIo.selectVoucherType();
        double discountValue = consoleIo.readDouble("discount");
        return new VoucherCreateRequest(type, discountValue);
    }
}
