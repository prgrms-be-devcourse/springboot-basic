package com.programmers.springweekly;

import com.programmers.springweekly.controller.CustomerController;
import com.programmers.springweekly.controller.VoucherController;
import com.programmers.springweekly.controller.WalletController;
import com.programmers.springweekly.domain.WalletMenu;
import com.programmers.springweekly.dto.wallet.request.WalletCreateRequest;
import com.programmers.springweekly.dto.wallet.response.WalletResponse;
import com.programmers.springweekly.dto.wallet.response.WalletsResponse;
import com.programmers.springweekly.view.Console;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsoleWallet {

    private final WalletController walletController;
    private final CustomerController customerController;
    private final VoucherController voucherController;
    private final Console console;

    public void run() {
        console.outputWalletMenuGuide();
        WalletMenu walletMenu = WalletMenu.from(console.inputMessage());

        switch (walletMenu) {
            case ASSIGN -> createWallet();
            case DELETE -> deleteWallet();
            case FINDALL -> findAllWallet();
            case FINDBYCUSTOMER -> findWalletByCustomerId();
            case FINDBYVOUCHER -> findWalletByVoucherId();
            default -> throw new IllegalArgumentException("Input :" + walletMenu + "찾으시는 바우처 지갑 메뉴가 없습니다.");
        }
    }

    private void createWallet() {
        console.outputWalletCustomerUUIDGuide();
        UUID customerId = console.inputUUID();
        customerController.findById(customerId);

        console.outputWalletVoucherUUIDGuide();
        UUID voucherId = console.inputUUID();
        voucherController.findById(voucherId);

        walletController.save(new WalletCreateRequest(customerId, voucherId));
    }

    private void deleteWallet() {
        console.outputWalletUUIDGuide();
        UUID walletId = console.inputUUID();
        walletController.deleteByWalletId(walletId);
    }

    private void findAllWallet() {
        WalletsResponse walletsResponse = walletController.findAll();
        console.outputGetWalletList(walletsResponse);
    }

    private void findWalletByCustomerId() {
        console.outputWalletCustomerUUIDToFind();
        WalletResponse walletResponse = walletController.findByCustomerId(console.inputUUID());
        console.outputGetWallet(walletResponse);
    }

    private void findWalletByVoucherId() {
        console.outputWalletVoucherUUIDToFind();
        WalletsResponse walletsResponse = walletController.findByVoucherId(console.inputUUID());
        console.outputGetWalletListByVoucher(walletsResponse);
    }
}
