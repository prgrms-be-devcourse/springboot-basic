package com.programmers.kwonjoosung.springbootbasicjoosung.controller.wallet;

import com.programmers.kwonjoosung.springbootbasicjoosung.console.Console;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.Command;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.programmers.kwonjoosung.springbootbasicjoosung.console.message.WalletResponseMessage.*;

@Component
public class WalletController {
    private final WalletService walletService;
    private final Console console;

    public WalletController(WalletService walletService, Console console) {
        this.walletService = walletService;
        this.console = console;
    }

    public void execute(Command command) {
        switch (command) {
            case CREATE -> insertWallet();
            case DELETE -> deleteWallet();
        }
    }

    private void insertWallet() {
        UUID customerId = console.getCustomerId();
        UUID voucherId = console.getVoucherId();
        boolean result = walletService.insertToWallet(customerId, voucherId);
        if (result) console.printMessage(INSERT_WALLET_SUCCESS);
        else console.printMessage(INSERT_WALLET_FAIL);

    }

    private void deleteWallet() {
        UUID voucherId = console.getVoucherId();
        boolean result = walletService.deleteVoucherFromWallet(voucherId);
        if (result) console.printMessage(DELETE_WALLET_SUCCESS);
        else console.printMessage(DELETE_WALLET_FAIL);
    }

}
