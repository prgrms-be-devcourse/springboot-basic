package org.prgms.kdtspringweek1.controller;

import org.prgms.kdtspringweek1.console.ConsoleOutput;
import org.prgms.kdtspringweek1.controller.dto.SelectFunctionTypeDto;
import org.prgms.kdtspringweek1.voucher.service.dto.CreateVoucherRequestDto;
import org.prgms.kdtspringweek1.voucher.service.dto.FindVoucherResponseDto;
import org.prgms.kdtspringweek1.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class VoucherController {
    private final ConsoleInputConverter consoleInputConverter;
    private final ConsoleOutput consoleOutput;
    private final VoucherService voucherService;

    public VoucherController(ConsoleInputConverter consoleInputConverter, ConsoleOutput consoleOutput, VoucherService voucherService) {
        this.consoleInputConverter = consoleInputConverter;
        this.consoleOutput = consoleOutput;
        this.voucherService = voucherService;
    }

    public void selectVoucherFunction(SelectFunctionTypeDto functionTypeDto) {
        switch (functionTypeDto) {
            case CREATE_VOUCHER -> createVoucher();
            case LIST_VOUCHERS -> getAllVouchers();
            case SEARCH_VOUCHER -> getVoucherById();
            case UPDATE_VOUCHER -> updateVoucherInfo();
            case DELETE_ALL_VOUCHERS -> deleteAllVouchers();
            case DELETE_VOUCHER -> deleteVoucher();
        }
    }

    private void createVoucher() {
        consoleOutput.printVouchersToSelect();
        String voucherType = consoleInputConverter.getVoucherTypeString();
        consoleOutput.printRequestMessageforDiscountValue();
        long discountValue = consoleInputConverter.getDiscountValue();
        voucherService.registerVoucher(new CreateVoucherRequestDto(discountValue, voucherType).toVoucher());
        consoleOutput.printSuccessToCreate();
    }

    private void getAllVouchers() {
        voucherService.searchAllVouchers()
                .forEach(FindVoucherResponseDto::printVoucherInfo);
        consoleOutput.printSuccessToSearch();
    }

    private void getVoucherById() {
        consoleOutput.printRequestMessageForVoucherId();
        voucherService.searchVoucherById(consoleInputConverter.getId())
                .ifPresentOrElse(
                        findVoucherResponseDto -> {
                            findVoucherResponseDto.printVoucherInfo();
                            consoleOutput.printSuccessToSearch();
                        },
                        consoleOutput::printValueNotFound
                );
    }

    private void updateVoucherInfo() {
        consoleOutput.printRequestMessageForVoucherId();
        UUID voucherId = consoleInputConverter.getId();
        consoleOutput.printVouchersToSelect();
        switch (consoleInputConverter.getVoucherType()) {
            case FIXED_AMOUNT -> {
                consoleOutput.printRequestMessageforDiscountValue();
                voucherService.update(consoleInputConverter.getUpdateVoucherRequestDto(voucherId).toFixedAmountVoucher());
            }
            case PERCENT_DISCOUNT -> {
                consoleOutput.printRequestMessageforDiscountValue();
                voucherService.update(consoleInputConverter.getUpdateVoucherRequestDto(voucherId).toPercentDiscountVoucher());
            }
        }
        consoleOutput.printSuccessToUpdate();
    }

    private void deleteVoucher() {
        consoleOutput.printRequestMessageForVoucherId();
        voucherService.deleteVoucherById(consoleInputConverter.getId());
        consoleOutput.printSuccessToDelete();
    }

    private void deleteAllVouchers() {
        voucherService.deleteAllVouchers();
        consoleOutput.printSuccessToDelete();
    }
}
