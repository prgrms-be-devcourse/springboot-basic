package org.prgrms.kdtspringdemo.wallet.controller;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.wallet.domain.Wallet;
import org.prgrms.kdtspringdemo.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.UUID;
import java.util.List;

@Controller
public class WalletController {
    private final WalletService walletService;
    private final OutputConsole outputConsole = new OutputConsole();
    private final InputConsole inputConsole = new InputConsole();
    private final Logger logger = LoggerFactory.getLogger(WalletController.class);


    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    public void printVouchersByCustomerId() {
        try {
            outputConsole.getCustomerId();
            UUID customerId = UUID.fromString(inputConsole.getString());
            List<Voucher> vouchers = walletService.findVouchersById(customerId);
            vouchers.stream().forEach(outputConsole::printVoucher);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void insertVoucherByCustomerId() {
        try {
            outputConsole.getWalletId();
            UUID walletId = UUID.fromString(inputConsole.getString());
            outputConsole.getCustomerId();
            UUID customerId = UUID.fromString(inputConsole.getString());
            outputConsole.getVoucherId();
            UUID voucherId = UUID.fromString(inputConsole.getString());
            walletService.addVoucherByCustomerId(walletId, customerId, voucherId);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    // customer park2 : 6378ec61-9de8-4448-aebf-283c0d66d0be
    // voucher : 61013580-6aae-40bd-aa89-2ed4bc61f1c0

    public void deleteVoucherByVoucherId() {
        try {
            outputConsole.getCustomerId();
            UUID customerId = UUID.fromString(inputConsole.getString());
            outputConsole.getVoucherId();
            UUID voucherId = UUID.fromString(inputConsole.getString());
            walletService.deleteVoucherByVoucherId(customerId, voucherId);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void printAllWallet() {
        List<Wallet> walletList = walletService.findAll();
        walletList.stream().forEach(outputConsole::printWallet);
    }

    public void endWalletMode() {
        outputConsole.printWalletModeEnd();
    }
}
