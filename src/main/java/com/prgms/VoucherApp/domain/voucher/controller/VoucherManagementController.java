package com.prgms.VoucherApp.domain.voucher.controller;

import com.prgms.VoucherApp.domain.voucher.VoucherCommand;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateReqDto;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResDto;
import com.prgms.VoucherApp.domain.voucher.model.VoucherDao;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class VoucherManagementController implements Runnable {

    private final VoucherDao voucherDao;
    private final Input input;
    private final Output output;

    public VoucherManagementController(VoucherDao voucherDao, Input input, Output output) {
        this.voucherDao = voucherDao;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            output.printVoucherCommand();
            VoucherCommand command = VoucherCommand.findByVoucherCommandNumber(input.inputVoucherCommand());

            if (command.isCreate()) {
                output.printDisplayVoucherPolicy();
                String inputVoucherType = input.inputVoucherType();
                VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);
                output.printDisplayDiscountCondition(voucherType);
                Long amount = input.inputDiscountAmount(voucherType);
                VoucherCreateReqDto voucherCreateReqDto = new VoucherCreateReqDto(voucherType, BigDecimal.valueOf(amount));
                voucherDao.save(voucherCreateReqDto);
                continue;
            }

            if (command.isFindAll()) {
                VouchersResDto findVouchers = voucherDao.findAll();
                output.printVoucherList(findVouchers.getVouchers());
                continue;
            }

            if (command.isFindOne()) {
                UUID inputUUID = UUID.fromString(input.inputUUID());
                voucherDao.findOne(inputUUID)
                    .ifPresentOrElse(output::printVoucher, output::printFindEmpty);
            }

            if (command.isExit()) {
                isRunning = false;
            }

            output.printNotImplementMsg();
        }
    }
}
