package com.prgms.vouchermanager.contorller.voucher;

import com.prgms.vouchermanager.contorller.front.FrontController;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.service.voucher.VoucherService;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import com.prgms.vouchermanager.util.io.ConsoleOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class VoucherController {

    private final static Logger logger = LoggerFactory.getLogger(FrontController.class);

    private final VoucherService voucherService;

    private final ConsoleInput consoleInput;

    private final ConsoleOutput consoleOutput;

    public VoucherController(VoucherService voucherService, ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        this.voucherService = voucherService;
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
    }

    public void create() {
        try{
            consoleOutput.printVoucherType();
            int voucherType = consoleInput.inputVoucherType();
            consoleOutput.printVoucherAmount();
            long value = consoleInput.inputVoucherValue();
            voucherService.create(new CreateVoucherDto(value,voucherType));

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            logger.warn(e.getMessage());
        }
    }

    public void getList() {
        List<Voucher> voucherList = voucherService.getVoucherList();

        voucherList.forEach(voucher -> System.out.println(voucher.toString()));

    }
}
