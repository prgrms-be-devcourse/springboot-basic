package org.weekly.weekly.voucher.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.weekly.weekly.ui.CommandLineApplication;
import org.weekly.weekly.util.PrintMsg;
import org.weekly.weekly.util.VoucherMenu;
import org.weekly.weekly.voucher.dto.VoucherDto;
import org.weekly.weekly.voucher.dto.Response;
import org.weekly.weekly.voucher.service.VoucherService;

@Controller
public class VoucherController {
    private final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;
    private final CommandLineApplication commandLineApplication;

    public VoucherController(VoucherService voucherService, CommandLineApplication commandLineApplication) {
        this.voucherService = voucherService;
        this.commandLineApplication = commandLineApplication;
    }

    public void start() {
        boolean isStop = false;

        while(!isStop) {
            try {
                isStop = selectMenu(this.commandLineApplication.readMenu());
            } catch (RuntimeException runtimeException) {
                this.commandLineApplication.printErrorMsg(runtimeException.getMessage());
            }
        }
    }

    private boolean selectMenu(VoucherMenu voucherMenu) {
        if (VoucherMenu.CREATE.equals(voucherMenu)) {
            createVoucher();
            return false;
        }

        if (VoucherMenu.LIST.equals(voucherMenu)) {
            getList();
            return false;
        }

        return true;
    }

    private void createVoucher() {
        VoucherDto voucherDto = this.commandLineApplication.readVoucher();
        Response response = this.voucherService.insertVoucher(voucherDto);
        logger.info("{}{}",PrintMsg.CREATE_VOUCHER_SUCCESS.getMsg(),response.getResult());
        this.commandLineApplication.printResult(response);
    }

    private void getList() {
        Response response = this.voucherService.getVouchers();
        logger.info("{}{}",PrintMsg.FIND_ALL_VOUCHER_SUCCESS.getMsg());
        this.commandLineApplication.printResult(response);
    }
}
