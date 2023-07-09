package com.programmers.voucher.controller;

import static com.programmers.util.ValueFormatter.changeDiscountValueToNumber;
import static com.programmers.util.ValueFormatter.reformatVoucherType;

import com.programmers.exception.InvalidRequestValueException;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.VoucherCreateRequestDto;
import com.programmers.voucher.dto.VoucherDto;
import com.programmers.voucher.dto.VouchersResponseDto;
import com.programmers.io.Console;
import com.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        VoucherCreateRequestDto voucherCreateRequestDto = new VoucherCreateRequestDto(voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType());
        voucherService.save(voucherCreateRequestDto);
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

        return VoucherType.createVoucher(voucherTypeInput, voucherName, discountValue, Optional.empty());
    }

    public List<Voucher> getVoucherList() {
        console.printVoucherListTitle();
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();

        return getVouchersResult(vouchersResponseDto.vouchers());
    }

    public List<Voucher> getVouchersResult(List<Voucher> vouchers) {
        if (vouchers.isEmpty()) {
            console.printVoucherListEmptyMessage();
            return vouchers;
        }

        console.printVouchers(vouchers);
        log.info("The voucher list has been printed.");
        return vouchers;
    }

    public void updateVoucher() {
        if (getVoucherList().isEmpty()) {
            return;
        }

        Voucher originalVoucher = getVoucherToUpdate();
        VoucherDto voucherDto = makeVoucherRequestDtoToUpdate(originalVoucher);

        voucherService.update(voucherDto);
        console.printUpdateVoucherCompleteMessage();
        log.info("The voucher has been updated.");
    }

    public Voucher getVoucherToUpdate() {
        console.printUpdateVoucherIdMessage();
        UUID updateVoucherId = UUID.fromString(console.readInput());

        VoucherDto voucherDto = voucherService.findById(updateVoucherId);

        return VoucherType.createVoucher(voucherDto.type().toString(), voucherDto.id(), voucherDto.name(), voucherDto.value(), voucherDto.customerId());
    }

    private VoucherDto makeVoucherRequestDtoToUpdate(Voucher voucher) {
        console.printUpdateNewVoucherValueMessage();
        Long updateVoucherValue = changeDiscountValueToNumber(console.readInput());

        console.printUpdateNewVoucherNameMessage();
        String updateVoucherName = console.readInput();

        return new VoucherDto(voucher.getVoucherId(), updateVoucherName, updateVoucherValue, voucher.getVoucherType(), voucher.getCustomerId());
    }

    public void deleteVoucher() {
        if (getVoucherList().isEmpty()) {
            return;
        }

        console.printDeleteTypeVoucherSelectionMessage();
        String command = console.readInput();
        checkDeleteTypeSelection(command);

        switch (command) {
            case DELETE_ONE_VOUCHER_NUMBER -> deleteOneVoucher();
            case DELETE_ALL_VOUCHERS_NUMBER -> deleteAllVouchers();
        }
    }

    private void checkDeleteTypeSelection(String deleteTypeSelection) {
        if (deleteTypeSelection.isEmpty()) {
            throw new InvalidRequestValueException("[ERROR] Delete Type 번호가 비었습니다.");
        }

        if (!deleteTypeSelection.equals(DELETE_ONE_VOUCHER_NUMBER) && !deleteTypeSelection.equals(DELETE_ALL_VOUCHERS_NUMBER)) {
            throw new InvalidRequestValueException("[ERROR] Delete Type 번호가 유효하지 않습니다.");
        }
    }

    public void deleteOneVoucher() {
        console.printDeleteVoucherIdMessage();
        UUID deleteVoucherId = UUID.fromString(console.readInput());

        voucherService.deleteById(deleteVoucherId);
        console.printDeleteVoucherCompleteMessage();
        log.info("The voucher has been deleted.");
    }

    public void deleteAllVouchers() {
        voucherService.deleteAll();
        console.printDeleteAllVouchersCompleteMessage();
        log.info("All vouchers have been deleted.");
    }
}
