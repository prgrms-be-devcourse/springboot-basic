package com.prgms.vouchermanager.contorller.wallet;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.domain.wallet.Wallet;
import com.prgms.vouchermanager.dto.CreateWalletDto;
import com.prgms.vouchermanager.service.wallet.WalletService;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import com.prgms.vouchermanager.util.io.ConsoleOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
public class WalletController {

    private final WalletService walletService;
    private final ConsoleOutput consoleOutput;
    private final ConsoleInput consoleInput;

    public WalletController(WalletService walletService, ConsoleOutput consoleOutput, ConsoleInput consoleInput) {
        this.walletService = walletService;
        this.consoleOutput = consoleOutput;
        this.consoleInput = consoleInput;
    }

    public void create() {

        try {
            consoleOutput.printCustomerId();
            Long customerId = consoleInput.inputCustomerId();
            consoleOutput.printVoucherId();
            UUID voucherId = consoleInput.inputVoucherId();
            Wallet wallet = walletService.save(new CreateWalletDto(customerId, voucherId));
            consoleOutput.printWallet(wallet);

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }

    }

    public void deleteByCustomerId() {

        try {
            consoleOutput.printCustomerId();
            Long customerId = consoleInput.inputCustomerId();
            walletService.deleteByCustomerId(customerId);
            consoleOutput.printSuccessDelete();

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }
    }

    public void findByCustomerId() {

        try {
            consoleOutput.printCustomerId();
            Long customerId = consoleInput.inputCustomerId();
            List<Wallet> wallets =  walletService.findByCustomerId(customerId);
            wallets.forEach(wallet -> consoleOutput.printWallet(wallet));

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }

    }

    public void findByVoucherId() {

        try {

            consoleOutput.printVoucherId();
            UUID voucherId = consoleInput.inputVoucherId();

            Wallet wallet = walletService.findByVoucherId(voucherId);
            consoleOutput.printWallet(wallet);

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }

    }
}
