package com.programmers.controller;

import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.VoucherType;
import com.programmers.domain.voucher.dto.VoucherCreateRequestDto;
import com.programmers.domain.voucher.dto.VoucherResponseDto;
import com.programmers.domain.voucher.dto.VoucherUpdateRequestDto;
import com.programmers.domain.voucher.dto.VouchersResponseDto;
import com.programmers.io.Console;
import com.programmers.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.UUID;

import static com.programmers.util.ValueFormatter.changeDiscountValueToNumber;
import static com.programmers.util.ValueFormatter.reformatVoucherType;

@Controller
public class VoucherController {

    private static final Logger log = LoggerFactory.getLogger(VoucherController.class);

    private static final String DELETE_ONE_VOUCHER_NUMBER = "1";
    private static final String DELETE_ALL_VOUCHERS_NUMBER = "2";

    private final Console console;
    private final VoucherService voucherService;

    public VoucherController(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public Voucher createVoucher() {
        Voucher voucher = makeVoucher();
        voucherService.save(new VoucherCreateRequestDto(voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType()));
        console.printVoucherCreated();
        log.info("The voucher has been created.");

        return voucher;
    }

    public Voucher makeVoucher() {
        console.printVoucherType();
        String voucherTypeInput = reformatVoucherType(console.readInput());

        console.printDiscountValueInput();
        Long discountValue = changeDiscountValueToNumber(console.readInput());

        console.printVoucherNameInput();
        String voucherName = console.readInput();

        return VoucherType.createVoucher(voucherTypeInput, voucherName, discountValue);
    }

    public void getVoucherList() {
        console.printVoucherListTitle();
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();
        console.printVouchers(vouchersResponseDto);
        log.info("The voucher list has been printed.");
    }

    public void updateVoucher() {
        getVoucherList();

        Voucher originalVoucher = getVoucherToUpdate();
        VoucherUpdateRequestDto voucherUpdateRequestDto = makeVoucherRequestDtoToUpdate(originalVoucher);

        voucherService.update(voucherUpdateRequestDto);
        console.printUpdateVoucherCompleteMessage();
    }

    public Voucher getVoucherToUpdate() {
        console.printUpdateVoucherIdMessage();
        UUID updateVoucherId = UUID.fromString(console.readInput());

        VoucherResponseDto voucherResponseDto = voucherService.findById(updateVoucherId);

        return VoucherType.createVoucher(voucherResponseDto.type().toString(), voucherResponseDto.id(), voucherResponseDto.name(), voucherResponseDto.value());
    }

    private VoucherUpdateRequestDto makeVoucherRequestDtoToUpdate(Voucher voucher) {
        console.printUpdateNewVoucherValueMessage();
        Long updateVoucherValue = changeDiscountValueToNumber(console.readInput());

        console.printUpdateNewVoucherNameMessage();
        String updateVoucherName = console.readInput();

        return new VoucherUpdateRequestDto(voucher.getVoucherId(), updateVoucherName, updateVoucherValue, voucher.getVoucherType());
    }

    public void deleteVoucher() {
        console.printDeleteTypeVoucherSelectionMessage();
        String command = console.readInput();
        checkDeleteTypeSelection(command);

        switch (command) {
            case DELETE_ONE_VOUCHER_NUMBER -> deleteOneVoucher();
            case DELETE_ALL_VOUCHERS_NUMBER -> deleteAllVouchers();
        }
    }

    private void checkDeleteTypeSelection(String input) {
        if (!input.equals(DELETE_ONE_VOUCHER_NUMBER) && !input.equals(DELETE_ALL_VOUCHERS_NUMBER)) {
            throw new IllegalArgumentException();
        }
    }

    public void deleteOneVoucher() {
        getVoucherList();

        console.printDeleteVoucherIdMessage();
        UUID deleteVoucherId = UUID.fromString(console.readInput());

        voucherService.deleteById(deleteVoucherId);
        console.printDeleteVoucherCompleteMessage();
    }

    public void deleteAllVouchers() {
        getVoucherList();

        voucherService.deleteAll();
        console.printDeleteAllVouchersCompleteMessage();
    }
}
