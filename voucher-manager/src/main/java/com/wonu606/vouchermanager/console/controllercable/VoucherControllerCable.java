package com.wonu606.vouchermanager.console.controllercable;

import com.wonu606.vouchermanager.console.controllercable.menu.VoucherMenu;
import com.wonu606.vouchermanager.controller.VoucherController;
import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWalletDto;
import com.wonu606.vouchermanager.domain.customer.Customer;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.console.consoleio.concreate.VoucherConsoleIO;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class VoucherControllerCable {

    private final VoucherConsoleIO voucherConsoleIO;
    private final VoucherController controller;

    public VoucherControllerCable(VoucherConsoleIO voucherConsoleIO,
            VoucherController controller) {
        this.voucherConsoleIO = voucherConsoleIO;
        this.controller = controller;
    }

    public void run() {
        VoucherMenu menu = VoucherMenu.START;
        while (menu.isNotExit()) {
            try {
                menu = voucherConsoleIO.selectMenu();
                executeMenuAction(menu);
            } catch (Exception exception) {
                voucherConsoleIO.displayMessage(exception.getMessage());
            }
        }
    }

    private void executeMenuAction(VoucherMenu menu) {
        switch (menu) {
            case EXIT:
                return;

            case LIST:
                List<Voucher> voucherList = controller.getVoucherList();
                voucherConsoleIO.displayVoucherList(voucherList);
                return;

            case CREATE:
                VoucherDto voucherDto = createVoucherDto();
                controller.createVoucher(voucherDto);
                return;
            case ASSIGN:
                VoucherWalletDto walletDto = createWalletDto();
                controller.assignWallet(walletDto);
                return;

            case CUSTOMER_LIST:
                String voucherUuId = voucherConsoleIO.readString("Voucher UUID");
                List<Customer> customerList = controller.getCustomersOwnedByVoucherId(voucherUuId);
                voucherConsoleIO.displayCustomerList(customerList);
                return;

            default:
                throw new IllegalArgumentException("수행할 수 없는 메뉴입니다.");
        }
    }

    private VoucherWalletDto createWalletDto() {
        String uuid = voucherConsoleIO.readString("Voucher UUID");
        String emailAddress = voucherConsoleIO.readString("Customer EmailAddress");
        return new VoucherWalletDto(UUID.fromString(uuid), emailAddress);
    }

    private VoucherDto createVoucherDto() {
        String type = voucherConsoleIO.selectVoucherType();
        double discountValue = voucherConsoleIO.readDouble("discount");
        return new VoucherDto(type, discountValue);
    }
}
