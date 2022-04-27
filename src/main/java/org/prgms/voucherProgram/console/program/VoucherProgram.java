package org.prgms.voucherProgram.console.program;

import java.util.UUID;

import org.prgms.voucherProgram.console.menu.VoucherMenuType;
import org.prgms.voucherProgram.console.view.Console;
import org.prgms.voucherProgram.console.view.InputView;
import org.prgms.voucherProgram.console.view.OutputView;
import org.prgms.voucherProgram.domain.voucher.VoucherType;
import org.prgms.voucherProgram.dto.VoucherRequest;
import org.prgms.voucherProgram.dto.WalletRequest;
import org.prgms.voucherProgram.exception.VoucherIsNotExistsException;
import org.prgms.voucherProgram.exception.WrongCommandException;
import org.prgms.voucherProgram.exception.WrongDiscountValueException;
import org.prgms.voucherProgram.exception.WrongEmailException;
import org.prgms.voucherProgram.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherProgram {
    private final VoucherService voucherService;
    private final InputView inputView;
    private final OutputView outputView;

    public VoucherProgram(VoucherService voucherService, Console console) {
        this.voucherService = voucherService;
        this.inputView = console;
        this.outputView = console;
    }

    public void run() {
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            VoucherMenuType voucherMenuType = inputMenu();
            switch (voucherMenuType) {
                case EXIT -> isNotEndProgram = false;
                case CREATE -> createVoucher();
                case LIST -> outputView.printVouchers(voucherService.findAllVoucher());
                case UPDATE -> updateVoucher();
                case DELETE -> deleteVoucher();
                case WALLET -> walletVoucher();
            }
        }
    }

    private VoucherMenuType inputMenu() {
        while (true) {
            try {
                return VoucherMenuType.fromMainMenu(inputView.inputVoucherMenu());
            } catch (WrongCommandException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void createVoucher() {
        VoucherType voucherType = inputVoucherType();
        VoucherRequest voucherRequest = inputView.inputVoucherInformation(voucherType.getNumber());
        while (true) {
            try {
                outputView.printVoucher(voucherService.create(voucherRequest));
                return;
            } catch (WrongDiscountValueException e) {
                outputView.printError(e.getMessage());
                voucherRequest.setDiscountValue(inputView.inputDiscountValue());
            }
        }
    }

    private VoucherType inputVoucherType() {
        while (true) {
            try {
                return VoucherType.findByNumber(inputView.inputVoucherType());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void updateVoucher() {
        UUID voucherId = inputView.inputVoucherId();
        VoucherType voucherType = inputVoucherType();
        VoucherRequest voucherRequest = inputView.inputVoucherInformation(voucherType.getNumber());
        while (true) {
            try {
                outputView.printVoucher(voucherService.update(voucherId, voucherRequest));
                return;
            } catch (WrongDiscountValueException e) {
                outputView.printError(e.getMessage());
                voucherRequest.setDiscountValue(inputView.inputDiscountValue());
            } catch (VoucherIsNotExistsException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }
    }

    private void deleteVoucher() {
        UUID voucherId = inputView.inputVoucherId();
        try {
            voucherService.delete(voucherId);
            outputView.printSuccess();
        } catch (VoucherIsNotExistsException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void walletVoucher() {
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            VoucherMenuType walletMenuType = inputWalletMenu();
            switch (walletMenuType) {
                case EXIT -> isNotEndProgram = false;
                case ASSIGN -> assignVoucher();
                case LIST -> findAssignVouchers();
                case DELETE -> deleteAssignVoucher();
                case FIND -> findCustomerByVoucher();
            }
        }
    }

    private void deleteAssignVoucher() {
        WalletRequest walletRequest = inputView.inputWalletInformation();
        try {
            voucherService.deleteAssignVoucher(walletRequest);
            outputView.printSuccess();
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void findCustomerByVoucher() {
        UUID voucherId = inputView.inputVoucherId();
        try {
            outputView.printCustomer(voucherService.findCustomer(voucherId));
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void assignVoucher() {
        WalletRequest walletRequest = inputView.inputWalletInformation();
        try {
            outputView.printVoucher(voucherService.assignVoucher(walletRequest));
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
        }
    }

    private VoucherMenuType inputWalletMenu() {
        while (true) {
            try {
                return VoucherMenuType.fromSubMenu(inputView.inputWalletMenu());
            } catch (WrongCommandException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void findAssignVouchers() {
        String email = inputView.inputCustomerEmail();
        while (true) {
            try {
                outputView.printVouchers(voucherService.findAssignVouchers(email));
                return;
            } catch (WrongEmailException e) {
                outputView.printError(e.getMessage());
                email = inputView.inputCustomerEmail();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }
    }
}
