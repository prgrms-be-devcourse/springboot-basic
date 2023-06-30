package com.prgrms.spring.controller.voucher;

import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.exception.Error;
import com.prgrms.spring.exception.Success;
import com.prgrms.spring.io.ConsoleView;
import com.prgrms.spring.service.voucher.VoucherService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@Controller
@AllArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    private final ConsoleView consoleView;
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public void createVoucher() {

        consoleView.showVoucherTypes();
        VoucherType type = VoucherType.matchType(consoleView.getVoucherType());
        consoleView.showVoucherPrompt(type);
        Long discount = consoleView.getVoucherDiscount();
        Voucher voucher = voucherService.createVoucher(type, discount);
        if (voucher == null) {
            consoleView.showErrorMsg(Error.CREATE_VOUCHER_EXCEPTION);
            logger.error("바우처 생성 실패");
            return;
        }
        consoleView.showSuccessMsg(Success.CREATE_VOUCHER_SUCCESS);
    }

    public void getAllVoucher() {
        consoleView.showAllVouchers(voucherService.getAllVoucher());
    }
}
