package com.prgms.VoucherApp.domain.voucher.controller;

import com.prgms.VoucherApp.domain.voucher.Voucher;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherDto;
import com.prgms.VoucherApp.domain.voucher.model.VoucherCreator;
import com.prgms.VoucherApp.domain.voucher.model.VoucherReader;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherManagementController {

    private final VoucherCreator voucherCreator;
    private final VoucherReader voucherReader;
    private final Input input;
    private final Output output;

    public VoucherManagementController(VoucherCreator voucherCreator, VoucherReader voucherReader, Input input, Output output) {
        this.voucherCreator = voucherCreator;
        this.voucherReader = voucherReader;
        this.input = input;
        this.output = output;
    }

    public void createVoucher() {
        VoucherType voucherType = getVoucherType();
        long amount = getDiscountAmount(voucherType);

        try {
            Voucher voucher = voucherCreator.createVoucher(voucherType, amount);
            output.printCreatedMsg(voucher);
        } catch (RuntimeException e) {
            output.printErrorMsg(e.getMessage());
        }

    }

    public void readVouchers() {
        try {
            List<VoucherDto> vouchers = voucherReader.readVoucherList();
            output.printVoucherList(vouchers);
        } catch (RuntimeException e) {
            output.printErrorMsg(e.getMessage());
        }
    }

    private VoucherType getVoucherType() {
        output.printDisplayVoucherPolicy();
        String inputVoucherTypeName = input.inputVoucherType();
        return VoucherType.findByVoucherTypeName(inputVoucherTypeName);
    }

    private long getDiscountAmount(VoucherType voucherType) {
        output.printDisplayDiscountCondition(voucherType);
        return input.inputDiscountAmount(voucherType);
    }
}
