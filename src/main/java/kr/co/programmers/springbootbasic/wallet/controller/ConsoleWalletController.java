package kr.co.programmers.springbootbasic.wallet.controller;

import kr.co.programmers.springbootbasic.io.Input;
import kr.co.programmers.springbootbasic.io.Output;
import kr.co.programmers.springbootbasic.io.enums.WalletServiceCommand;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletResponse;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveDto;
import kr.co.programmers.springbootbasic.wallet.service.WalletService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@Profile("console")
public class ConsoleWalletController {
    private final Input inputConsole;
    private final Output outputConsole;
    private final WalletService walletService;

    public ConsoleWalletController(Input inputConsole, Output outputConsole, WalletService walletService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.walletService = walletService;
    }

    public void doWalletService() {
        outputConsole.printWalletServiceMenu();
        WalletServiceCommand command = inputConsole.readWalletServiceCommand();
        switch (command) {
            case SAVE_VOUCHER_IN_WALLET -> saveVoucherInWallet();
            case FIND_WALLET_BY_WALLET_ID -> findWalletById();
        }
    }

    public void saveVoucherInWallet() {
        outputConsole.printVoucherUuidTypeMessage();
        String voucherId = inputConsole.readUUID();
        outputConsole.printWalletUuidTypeMessage();
        String walletId = inputConsole.readUUID();

        WalletSaveDto requestDto = new WalletSaveDto(voucherId, walletId);
        WalletSaveDto responseDto = walletService.saveVoucherInCustomerWallet(requestDto);

        outputConsole.printWalletSaveMessage(responseDto);
    }

    public void findWalletById() {
        outputConsole.printWalletUuidTypeMessage();
        String walletId = inputConsole.readUUID();

        List<VoucherResponse> responses = walletService.findWalletById(walletId);
        UUID walletUUID = ApplicationUtils.toUUID(walletId.getBytes());
        WalletResponse walletResponse = new WalletResponse(walletUUID, responses);

        outputConsole.printWalletFindMessage(walletResponse);
    }
}
