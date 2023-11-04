package org.prgms.kdtspringweek1.consoleController;

import org.prgms.kdtspringweek1.console.ConsoleOutput;
import org.prgms.kdtspringweek1.consoleController.dto.SelectFunctionTypeDto;
import org.prgms.kdtspringweek1.customer.service.dto.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.voucher.service.dto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.wallet.entity.Wallet;
import org.prgms.kdtspringweek1.wallet.service.WalletService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletController {
    private final ConsoleInputConverter consoleInputConverter;
    private final ConsoleOutput consoleOutput;
    private final WalletService walletService;

    public WalletController(ConsoleInputConverter consoleInputConverter, ConsoleOutput consoleOutput, WalletService walletService) {
        this.consoleInputConverter = consoleInputConverter;
        this.consoleOutput = consoleOutput;
        this.walletService = walletService;
    }

    public void selectVoucherFunction(SelectFunctionTypeDto functionTypeDto) {
        switch (functionTypeDto) {
            case CREATE_MY_WALLET -> createWallet();
            case SEARCH_MY_VOUCHERS -> getAllVouchersOwnedByCustomer();
            case DELETE_MY_WALLET -> deleteWallet();
            case SEARCH_MY_CUSTOMERS -> getAllCustomersOwnedByVoucher();
        }
    }

    private void createWallet() {
        consoleOutput.printRequestMessageForCustomerId();
        UUID customerId = consoleInputConverter.getId();
        consoleOutput.printRequestMessageForVoucherId();
        UUID voucherId = consoleInputConverter.getId();
        walletService.registerWallet(Wallet.createWithVoucherIdAndCustomerId(voucherId, customerId));
        consoleOutput.printSuccessToCreate();
    }

    private void getAllVouchersOwnedByCustomer() {
        consoleOutput.printRequestMessageForCustomerId();
        walletService.searchAllVouchersByCustomerId(consoleInputConverter.getId())
                .forEach(FindVoucherResponseDto::printVoucherInfo);
        consoleOutput.printSuccessToSearch();
    }

    private void deleteWallet() {
        consoleOutput.printRequestMessageForCustomerId();
        UUID customerId = consoleInputConverter.getId();
        consoleOutput.printRequestMessageForVoucherId();
        UUID voucherId = consoleInputConverter.getId();
        walletService.deleteWalletByVoucherIdAndCustomerId(voucherId, customerId);
        consoleOutput.printSuccessToDelete();
    }

    private void getAllCustomersOwnedByVoucher() {
        consoleOutput.printRequestMessageForVoucherId();
        walletService.searchAllCustomersByVoucherId(consoleInputConverter.getId())
                .forEach(FindCustomerResponseDto::printCustomerInfo);
        consoleOutput.printSuccessToSearch();
    }
}
