package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.model.VouchersStorage;
import com.prgms.springbootbasic.ui.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class VoucherListController implements VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherListController.class);
    private final Console console;
    private final VouchersStorage vouchersStorage;

    public VoucherListController(Console console, VouchersStorage vouchersStorage) {
        this.console = console;
        this.vouchersStorage = vouchersStorage;
    }

    @Override
    public boolean run() throws IOException {
        List<Voucher> vouchers = vouchersStorage.findAll();
        console.showVoucherList(vouchers);
        logger.info("저장된 바우처를 불러오는데 성공했습니다. size : {}", vouchers.size());
        return true;
    }

}
