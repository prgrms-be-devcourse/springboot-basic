package org.prgms.voucherProgram.domain.program;

import java.util.UUID;

import org.prgms.voucherProgram.domain.menu.VoucherMenuType;
import org.prgms.voucherProgram.domain.voucher.VoucherType;
import org.prgms.voucherProgram.dto.VoucherDto;
import org.prgms.voucherProgram.dto.WalletRequestDto;
import org.prgms.voucherProgram.exception.VoucherIsNotExistsException;
import org.prgms.voucherProgram.exception.WrongCommandException;
import org.prgms.voucherProgram.exception.WrongDiscountAmountException;
import org.prgms.voucherProgram.exception.WrongDiscountPercentException;
import org.prgms.voucherProgram.exception.WrongEmailException;
import org.prgms.voucherProgram.service.VoucherService;
import org.prgms.voucherProgram.view.Console;
import org.prgms.voucherProgram.view.InputView;
import org.prgms.voucherProgram.view.OutputView;
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
                return VoucherMenuType.from(inputView.inputVoucherMenu());
            } catch (WrongCommandException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void createVoucher() {
        VoucherType voucherType = inputVoucherType();
        VoucherDto voucherDto = inputView.inputVoucherInformation(voucherType.getNumber());
        while (true) {
            try {
                outputView.printVoucher(voucherService.create(voucherDto));
                return;
            } catch (WrongDiscountAmountException e) {
                outputView.printError(e.getMessage());
                voucherDto.setDiscountValue(inputView.inputDiscountAmount());
            } catch (WrongDiscountPercentException e) {
                outputView.printError(e.getMessage());
                voucherDto.setDiscountValue(inputView.inputDiscountPercent());
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
        VoucherDto voucherDto = inputView.inputUpdateVoucher();
        while (true) {
            try {
                outputView.printVoucher(voucherService.update(voucherDto));
                return;
            } catch (WrongDiscountAmountException e) {
                outputView.printError(e.getMessage());
                voucherDto.setDiscountValue(inputView.inputDiscountAmount());
            } catch (WrongDiscountPercentException e) {
                outputView.printError(e.getMessage());
                voucherDto.setDiscountValue(inputView.inputDiscountPercent());
            } catch (VoucherIsNotExistsException e) {
                outputView.printError(e.getMessage());
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                voucherDto.setType(inputView.inputVoucherType());
            }
        }
    }

    private void deleteVoucher() {
        UUID voucherId = inputView.inputVoucherId();
        try {
            voucherService.delete(voucherId);
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
        WalletRequestDto walletRequestDto = inputView.inputWalletInformation();
        try {
            voucherService.deleteAssignVoucher(walletRequestDto);
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
        WalletRequestDto walletRequestDto = inputView.inputWalletInformation();
        try {
            outputView.printVoucher(voucherService.assignVoucher(walletRequestDto));
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
                outputView.printWalletVouchers(voucherService.findAssignVouchers(email));
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
