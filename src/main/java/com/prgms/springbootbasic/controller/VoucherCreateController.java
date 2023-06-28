package com.prgms.springbootbasic.controller;

import com.prgms.springbootbasic.model.Voucher;
import com.prgms.springbootbasic.model.VouchersStorage;
import com.prgms.springbootbasic.ui.Console;
import com.prgms.springbootbasic.util.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class VoucherCreateController implements VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherCreateController.class);
    private final Console console;
    private final VouchersStorage vouchersStorage;

    public VoucherCreateController(Console console, VouchersStorage vouchersStorage) {
        this.console = console;
        this.vouchersStorage = vouchersStorage;
    }

    @Override
    public boolean run() throws IOException {
        Voucher voucher = createVoucher();
        vouchersStorage.save(voucher);
        logger.info("바우처 저장에 성공했습니다. voucherType : {} voucherId : {} number : {}", voucher.getVoucherType(), voucher.getVoucherId(), voucher.getNumber());
        return true;
    }

    private Voucher createVoucher() {
        String voucherTypeOfString = console.inputVoucherType();
        VoucherType voucherType = VoucherType.of(voucherTypeOfString);
        Long number = console.inputVoucherNumber();
        return voucherType.createVoucher(number);
    }

}
