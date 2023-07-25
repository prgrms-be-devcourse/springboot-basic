package com.wonu606.vouchermanager.console.controllercable.voucher;

import com.wonu606.vouchermanager.console.controllercable.voucher.io.VoucherConsoleIo;
import com.wonu606.vouchermanager.controller.voucherwallet.VoucherWalletController;
import com.wonu606.vouchermanager.controller.voucherwallet.reqeust.VoucherCreateRequest;
import com.wonu606.vouchermanager.controller.voucherwallet.reqeust.OwnedCustomersRequest;
import com.wonu606.vouchermanager.controller.voucherwallet.reqeust.WalletAssignRequest;
import org.springframework.stereotype.Component;

@Component
public class VoucherControllerCable {

    private final VoucherConsoleIo consoleIo;
    private final VoucherWalletController controller;

    public VoucherControllerCable(VoucherConsoleIo consoleIo,
            VoucherWalletController controller) {
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
                String voucherResponse = controller.getVoucherList();
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
                String ownedCustomerResponse = controller.getOwnedCustomersByVoucher(
                        new OwnedCustomersRequest(voucherId));
                consoleIo.displayCustomerList(ownedCustomerResponse);
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private WalletAssignRequest createVoucherWalletCreateRequest() {
        String uuid = consoleIo.readString("Voucher UUID");
        return new WalletAssignRequest(uuid);
    }

    private VoucherCreateRequest createVoucherCreateRequest() {
        String type = consoleIo.selectVoucherType();
        double discountValue = consoleIo.readDouble("discount");
        return new VoucherCreateRequest(type, discountValue);
    }
}
