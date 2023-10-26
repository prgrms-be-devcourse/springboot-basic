package org.prgrms.kdtspringdemo.voucher.controller;

import org.prgrms.kdtspringdemo.view.InputConsole;
import org.prgrms.kdtspringdemo.view.OutputConsole;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {
    private final VoucherService voucherService;
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;
    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(VoucherService voucherService, InputConsole inputConsole, OutputConsole outputConsole) {
        this.voucherService = voucherService;
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    private VoucherTypeFunction findVoucherType() throws IOException {
        outputConsole.getVoucherType();
        String voucherType = inputConsole.getString();
        return voucherService.getVoucherTypeFunction(voucherType);
    }

    public void createVoucher() {
        try {
            VoucherTypeFunction voucherType = findVoucherType();
            UUID voucherId = UUID.randomUUID();
            outputConsole.getVoucherAmount();
            long amount = Long.parseLong(inputConsole.getString());

            voucherService.createVoucher(voucherType, voucherId, amount);
        } catch (NumberFormatException e) {
            logger.error("올바른 숫자 형식이 아닙니다.");
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
    }

    public void showAllVouchers() {
        List<Voucher> voucherList = voucherService.findAll().get();
        voucherList.forEach(voucher -> outputConsole.printVoucher(voucher));
    }

    public void endProgram() {
        voucherService.endVoucherService();
    }


}
