package com.programmers.voucher.operator;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.Printer;
import com.programmers.voucher.domain.enums.VoucherType;
import com.programmers.voucher.domain.enums.VoucherOperation;
import com.programmers.voucher.domain.voucher.*;
import com.programmers.voucher.stream.voucher.JdbcVoucherStream;
import com.programmers.voucher.stream.voucher.VoucherStream;
import org.springframework.stereotype.Component;

@Component
public class VoucherOperator {

    private final Console console;
    private final Printer printer;
    private final VoucherFactory voucherFactory;
    private final VoucherStream voucherStream;

    public VoucherOperator(Console console, Printer printer, VoucherFactory voucherFactory, VoucherStream voucherStream) {
        this.console = console;
        this.printer = printer;
        this.voucherFactory = voucherFactory;
        this.voucherStream = voucherStream;
    }

    public void voucherOperation() {
        String operationInput = console.getVoucherOperation();
        VoucherOperation voucherType = VoucherOperation.convertStringToVoucherOperation(operationInput).orElseThrow(
                () -> new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해주세요.")
        );
        switch (voucherType) {
            case CREATE -> createVoucher();
            case FINDALL -> getVoucherList();
            case FINDBYID -> getVoucher();
            case UPDATE -> updateVoucher();
            case DELETEBYID -> deleteVoucher();
            case DELETEALL -> deleteAllVoucher();
        }
    }

    private void createVoucher() {
        Integer inputVersion = console.getVoucherVersion();
        VoucherType voucherType = decideVoucherType(inputVersion);
        createFixedAmountVoucher(voucherType);
        createPercentDiscountVoucher(voucherType);
    }

    private VoucherType decideVoucherType(Integer inputVersion) {
        return VoucherType.decideVoucherType(inputVersion).orElseThrow(
                () -> new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해주세요.")
        );
    }

    private void createFixedAmountVoucher(VoucherType voucherType) {
        Integer inputNumber;
        if (voucherType == VoucherType.FIXED) {
            inputNumber = console.getAmount();
            voucherFactory.createVoucher(voucherType, inputNumber);
        }
    }

    private void createPercentDiscountVoucher(VoucherType voucherType) {
        Integer inputNumber;
        if (voucherType == VoucherType.PERCENT) {
            inputNumber = console.getRate();
            voucherFactory.createVoucher(voucherType, inputNumber);
        }
    }

    private void getVoucher() {
        String voucherId = console.getVoucherId();
        printer.printVoucher(((JdbcVoucherStream) voucherStream).findById(voucherId));
    }

    private void getVoucherList() {
        printer.printListOfVoucher(voucherStream.findAll());
    }

    private void updateVoucher() {
        String voucherId = console.getVoucherId();
        Voucher voucher = ((JdbcVoucherStream) voucherStream).findById(voucherId);
        updateFixedAmountVoucher(voucher);
        updatePercentDiscountVoucher(voucher);
        ((JdbcVoucherStream) voucherStream).update(voucher);
    }

    private void updateFixedAmountVoucher(Voucher voucher) {
        Integer updateAmount;
        if (voucher instanceof FixedAmountVoucher) {
            updateAmount = console.getAmount();
            ((FixedAmountVoucher) voucher).setAmount(updateAmount);
        }
    }

    private void updatePercentDiscountVoucher(Voucher voucher) {
        Integer updateAmount;
        if (voucher instanceof PercentDiscountVoucher) {
            updateAmount = console.getRate();
            ((PercentDiscountVoucher) voucher).setRate(updateAmount);
        }
    }

    private void deleteAllVoucher() {
        ((JdbcVoucherStream) voucherStream).deleteAll();
    }

    private void deleteVoucher() {
        String voucherId = console.getVoucherId();
        ((JdbcVoucherStream) voucherStream).deleteById(voucherId);
    }
}
