package org.promgrammers.springbootbasic.domain.wallet.controller;

import org.promgrammers.springbootbasic.domain.wallet.dto.request.CreateWalletRequest;
import org.promgrammers.springbootbasic.domain.wallet.dto.response.WalletListResponse;
import org.promgrammers.springbootbasic.domain.wallet.dto.response.WalletResponse;
import org.promgrammers.springbootbasic.domain.wallet.model.DeleteWalletType;
import org.promgrammers.springbootbasic.domain.wallet.model.FindWalletType;
import org.promgrammers.springbootbasic.domain.wallet.service.WalletService;
import org.promgrammers.springbootbasic.view.Console;
import org.promgrammers.springbootbasic.view.CrudMenu;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class WalletController {

    private final Console console;
    private final WalletService walletService;


    public WalletController(Console console, WalletService walletService) {
        this.console = console;
        this.walletService = walletService;
    }

    public void execute() {
        String inputCommand = console.walletCommandGuide();
        CrudMenu crudMenu = CrudMenu.from(inputCommand);

        switch (crudMenu) {
            case CREATE -> {
                WalletResponse wallet = create();
                console.print("Voucher가 생성되었습니다. : \n   " + wallet.walletOutput());
            }
            case FIND_ALL -> {
                WalletListResponse wallets = findAll();
                console.print(wallets.walletListOutPut());
            }
            case FIND_ONE -> findByType();
            case DELETE -> deleteType();
        }
    }

    private WalletResponse create() {
        String customerId = console.askForCustomerId();
        String voucherId = console.askForVoucherId();
        CreateWalletRequest walletRequest = new CreateWalletRequest(UUID.fromString(voucherId), UUID.fromString(customerId));

        return walletService.create(walletRequest);
    }

    private WalletListResponse findAll() {
        return walletService.findAll();
    }

    private WalletResponse findById() {
        String requestId = console.askForVoucherId();
        UUID walletId = UUID.fromString(requestId);

        WalletResponse wallet = walletService.findById(walletId);
        console.print(wallet.walletOutput());

        return wallet;
    }

    private WalletListResponse findAllWalletByCustomerId() {
        String requestId = console.askForCustomerId();
        UUID customerId = UUID.fromString(requestId);

        WalletListResponse walletByVoucherId = walletService.findAllWalletByCustomerId(customerId);
        console.print(walletByVoucherId.walletListOutPut());

        return walletService.findAllWalletByCustomerId(customerId);
    }

    private WalletListResponse findAllWalletByVoucherId() {
        String requestId = console.askForVoucherId();
        UUID voucherId = UUID.fromString(requestId);

        WalletListResponse walletsByVoucherId = walletService.findAllWalletByVoucherId(voucherId);
        console.print(walletsByVoucherId.walletListOutPut());

        return walletsByVoucherId;
    }

    private void deleteAll() {
        walletService.deleteAll();
        console.print("모든 지갑이 삭제 되었습니다.");
    }

    private void deleteById() {
        String requestId = console.askForVoucherId();
        UUID walletId = UUID.fromString(requestId);

        walletService.deleteById(walletId);
        console.print("삭제 되었습니다.");
    }

    private void findByType() {
        String inputType = console.askForWalletFindType();
        FindWalletType findType = FindWalletType.from(inputType);

        switch (findType) {
            case WALLET_ID -> findById();

            case VOUCHER_ID -> findAllWalletByVoucherId();

            case CUSTOMER_ID -> findAllWalletByCustomerId();
        }
    }

    private void deleteType() {
        String inputType = console.askForWalletDeleteType();
        DeleteWalletType deleteType = DeleteWalletType.from(inputType);

        switch (deleteType) {
            case WALLET_ID -> deleteById();

            case ALL -> deleteAll();
        }
    }
}
