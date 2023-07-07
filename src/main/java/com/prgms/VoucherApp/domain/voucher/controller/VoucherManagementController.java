package com.prgms.VoucherApp.domain.voucher.controller;


import com.prgms.VoucherApp.domain.voucher.dto.VoucherCreateReqDto;
import com.prgms.VoucherApp.domain.voucher.dto.VoucherUpdateReqDto;
import com.prgms.VoucherApp.domain.voucher.dto.VouchersResDto;
import com.prgms.VoucherApp.domain.voucher.model.VoucherDaoHandler;
import com.prgms.VoucherApp.domain.voucher.model.VoucherType;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import com.prgms.VoucherApp.view.VoucherCommand;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.UUID;

@Controller
public class VoucherManagementController implements Runnable {

    private final VoucherDaoHandler voucherDaoHandler;
    private final Input input;
    private final Output output;

    public VoucherManagementController(VoucherDaoHandler voucherDaoHandler, Input input, Output output) {
        this.voucherDaoHandler = voucherDaoHandler;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            output.printVoucherCommand();
            Integer inputCommandNumber = input.inputVoucherCommand();
            VoucherCommand command = VoucherCommand.findByVoucherCommandNumber(inputCommandNumber);

            if (command.isCreate()) {
                output.printDisplayVoucherPolicy();
                String inputVoucherType = input.inputVoucherType();
                VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);
                output.printDisplayDiscountCondition(voucherType);
                Long amount = input.inputDiscountAmount(voucherType);
                VoucherCreateReqDto voucherCreateReqDto = new VoucherCreateReqDto(voucherType, BigDecimal.valueOf(amount));
                voucherDaoHandler.save(voucherCreateReqDto);
                continue;
            }

            if (command.isFindAll()) {
                VouchersResDto findVouchers = voucherDaoHandler.findAll();
                output.printVoucherList(findVouchers.getVouchers());
                continue;
            }

            if (command.isFindOne()) {
                String inputUUID = input.inputUUID();
                UUID voucherId = UUID.fromString(inputUUID);
                voucherDaoHandler.findOne(voucherId)
                    .ifPresentOrElse(output::printVoucher, output::printFindEmpty);
                continue;
            }

            if (command.isFindByVoucherType()) {
                String inputVoucherType = input.inputVoucherType();
                VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);
                VouchersResDto findVouchers = voucherDaoHandler.findByVoucherType(voucherType);
                output.printVoucherList(findVouchers.getVouchers());
                continue;
            }

            if (command.isUpdate()) {
                String inputUUID = input.inputUUID();
                UUID voucherId = UUID.fromString(inputUUID);
                String inputVoucherType = input.inputVoucherType();
                VoucherType voucherType = VoucherType.findByVoucherTypeName(inputVoucherType);
                Long inputAmount = input.inputDiscountAmount(voucherType);
                BigDecimal amount = BigDecimal.valueOf(inputAmount);
                VoucherUpdateReqDto voucherUpdateReqDto = new VoucherUpdateReqDto(voucherId, amount, voucherType);
                voucherDaoHandler.update(voucherUpdateReqDto);
                continue;
            }

            if (command.isDelete()) {
                String inputUUID = input.inputUUID();
                UUID voucherId = UUID.fromString(inputUUID);
                voucherDaoHandler.deleteById(voucherId);
                continue;
            }


            if (command.isExit()) {
                isRunning = false;
                continue;
            }

            output.printNotImplementMsg();
        }
    }
}
