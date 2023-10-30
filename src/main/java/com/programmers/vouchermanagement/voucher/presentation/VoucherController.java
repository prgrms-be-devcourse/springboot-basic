package com.programmers.vouchermanagement.voucher.presentation;

import com.programmers.vouchermanagement.utils.ConsoleInputManager;
import com.programmers.vouchermanagement.utils.ConsoleOutputManager;
import com.programmers.vouchermanagement.voucher.application.VoucherService;
import com.programmers.vouchermanagement.voucher.dto.VoucherRequestDto;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDto;
import com.programmers.vouchermanagement.exception.FileIOException;
import com.programmers.vouchermanagement.voucher.exception.IllegalDiscountException;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotFoundException;
import com.programmers.vouchermanagement.voucher.exception.VoucherNotUpdatedException;
import com.programmers.vouchermanagement.voucher.exception.VoucherTypeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final ConsoleInputManager consoleInputManager;
    private final ConsoleOutputManager consoleOutputManager;
    private final VoucherService voucherService;

    public VoucherController(ConsoleInputManager consoleInputManager, ConsoleOutputManager consoleOutputManager, VoucherService voucherService) {
        this.consoleInputManager = consoleInputManager;
        this.consoleOutputManager = consoleOutputManager;
        this.voucherService = voucherService;
    }

    public void createVoucher() {

        consoleOutputManager.printCreateVoucher();

        consoleOutputManager.printVoucherTypeMenu();
        String voucherType = consoleInputManager.inputString();

        consoleOutputManager.printDiscountRequest();
        Long discount = consoleInputManager.inputDiscount();

        try {
            voucherService.createVoucher(new VoucherRequestDto(voucherType, discount));

        } catch (IllegalDiscountException e) {
            logger.error(e.getMessage() + "Console Input : " + discount, e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;

        } catch (FileIOException e) {
            logger.error(e.getMessage(), e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;

        } catch (VoucherTypeNotFoundException e) {
            logger.error(e.getMessage() + "Console Input : " + voucherType, e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;
        }

        consoleOutputManager.printSuccessCreation();
    }

    public void readAllVoucher() {

        consoleOutputManager.printList();

        try {
            List<VoucherResponseDto> voucherResponseDtos = voucherService.readAllVoucher();
            consoleOutputManager.printVoucherInfo(voucherResponseDtos);

        } catch (VoucherNotFoundException | FileIOException | VoucherTypeNotFoundException e) {
            logger.error(e.getMessage(), e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }

    public void readVoucherById() {

        consoleOutputManager.printReadVoucherById();

        consoleOutputManager.printGetVoucherId();
        UUID voucherId = UUID.fromString(consoleInputManager.inputString());

        try {
            VoucherResponseDto voucherResponseDto = voucherService.readVoucherById(voucherId);
            consoleOutputManager.printVoucherInfo(new ArrayList<>() {{
                add(voucherResponseDto);
            }});

        } catch (VoucherNotFoundException e) {
            logger.error(e.getMessage() + "Console Input : " + voucherId, e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }

    public void updateVoucher() {

        consoleOutputManager.printUpdateVoucher();

        consoleOutputManager.printGetVoucherId();
        UUID voucherId = UUID.fromString(consoleInputManager.inputString());

        consoleOutputManager.printVoucherTypeMenu();
        String voucherType = consoleInputManager.inputString();

        consoleOutputManager.printDiscountRequest();
        Long discount = consoleInputManager.inputDiscount();

        try {
            voucherService.updateVoucher(voucherId, new VoucherRequestDto(voucherType, discount));

        } catch (VoucherNotFoundException e) {
            logger.error(e.getMessage() + "Console Input : " + voucherId, e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;

        } catch (IllegalDiscountException e) {
            logger.error(e.getMessage() + "Console Input : " + discount, e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;

        } catch (FileIOException | VoucherNotUpdatedException e) {
            logger.error(e.getMessage(), e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;

        } catch (VoucherTypeNotFoundException e) {
            logger.error(e.getMessage() + "Console Input : " + voucherType, e);

            consoleOutputManager.printReturnMain(e.getMessage());
            return;
        }

        consoleOutputManager.printSuccessUpdate();
    }

    public void removeAllVoucher() {

        consoleOutputManager.printRemoveVoucher();

        try {
            voucherService.removeAllVoucher();
        } catch (FileIOException e) {
            logger.error(e.getMessage(), e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }

    public void removeVoucherById() {

        consoleOutputManager.printRemoveVoucherById();
        UUID voucherId = UUID.fromString(consoleInputManager.inputString());

        try {
            voucherService.removeVoucherById(voucherId);

        } catch (VoucherNotFoundException e) {
            logger.error(e.getMessage() + "Console Input : " + voucherId, e);

            consoleOutputManager.printReturnMain(e.getMessage());
        }
    }
}
