package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.commendLine.ConsoleInput;
import org.prgrms.kdt.commendLine.ConsoleOutput;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public void create() throws IOException {
        boolean isRunning = true;
        while (isRunning){
            try {
                String inputType = ConsoleInput.getVoucherTypes();
                VoucherType voucherType = VoucherType.getType(inputType);
                voucherService.createVoucher(voucherType);
                isRunning = false;

            }catch (InvalidInputException e){
                ConsoleOutput.printMessage("지원하지 않는 바우처 입니다.");
            }
        }
    }

    public void findAll() throws IOException {
        List<Voucher> vouchers = voucherService.findAll();
        ConsoleOutput.printAllBoucher(vouchers);
    }
}
