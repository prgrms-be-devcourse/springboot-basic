package com.prgms.VoucherApp.domain.wallet.controller;

import com.prgms.VoucherApp.domain.voucher.model.VoucherService;
import com.prgms.VoucherApp.domain.wallet.dto.WalletCreateRequest;
import com.prgms.VoucherApp.domain.wallet.dto.WalletResponse;
import com.prgms.VoucherApp.domain.wallet.dto.WalletsResponse;
import com.prgms.VoucherApp.domain.wallet.model.WalletService;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import com.prgms.VoucherApp.view.WalletCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class WalletManagementController implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(WalletManagementController.class);
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final WalletService walletService;

    public WalletManagementController(Input input, Output output, VoucherService voucherService, WalletService walletService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.walletService = walletService;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            output.printWalletCommand();
            int inputWalletCommandNumber = input.inputWalletCommand();

            WalletCommand walletCommand = WalletCommand.findByCommandNumber(inputWalletCommandNumber);
            try {
                switch (walletCommand) {
                    case CREATE -> {
                        UUID customerId = inputUUID();
                        UUID voucherId = inputUUID();

                        WalletCreateRequest walletCreateRequest = new WalletCreateRequest(customerId, voucherId);
                        walletService.save(walletCreateRequest);
                    }

                    case FIND_ONE -> {
                        UUID walletId = inputUUID();

                        WalletResponse walletResponse = walletService.findOne(walletId);
                        output.printWallet(walletResponse);
                    }

                    case FIND_BY_VOUCHER_ID -> {
                        UUID voucherId = inputUUID();

                        WalletResponse walletResponse = walletService.findByVoucherId(voucherId);
                        output.printWallet(walletResponse);
                    }

                    case FIND_BY_CUSTOMER_ID -> {
                        UUID customerId = inputUUID();
                        WalletsResponse walletsResponse = walletService.findAllByCustomerId(customerId);
                        output.printWallets(walletsResponse);
                    }

                    case DELETE -> {
                        UUID walletId = inputUUID();
                        walletService.deleteById(walletId);
                    }

                    case DELETE_BY_VOUCHER_ID -> {
                        UUID voucherId = inputUUID();
                        voucherService.deleteById(voucherId);
                    }

                    case EXIT -> {
                        isRunning = false;
                    }
                }
            } catch (RuntimeException exception) {
                logger.debug("지갑 관리 프로그램 실행 중에 발생한 예외를 처리하였습니다.", exception);
                output.printErrorMsg(exception.getMessage());
            }
        }
    }

    private UUID inputUUID() {
        String inputCustomerId = input.inputUUID();
        UUID customerId = UUID.fromString(inputCustomerId);
        return customerId;
    }
}
