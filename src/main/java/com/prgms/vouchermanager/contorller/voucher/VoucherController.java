package com.prgms.vouchermanager.contorller.voucher;

import com.prgms.vouchermanager.contorller.front.FrontController;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.service.voucher.VoucherService;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.prgms.vouchermanager.contorller.voucher.VoucherMenuType.*;

@Controller
public class VoucherController {

    private final static Logger logger = LoggerFactory.getLogger(FrontController.class);

    private final VoucherService voucherService;

    private final ConsoleInput consoleInput;


    public VoucherController(VoucherService voucherService, ConsoleInput consoleInput) {
        this.voucherService = voucherService;
        this.consoleInput = consoleInput;
    }

    public void run() {
        int menu = 0;
        try {
            menu = consoleInput.inputVoucherMenu();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            logger.warn(e.getMessage());
            return;
        }
        if (menu == CREATE.getNumber()) {
            try {
                Voucher voucher = consoleInput.inputVoucher();
                voucherService.create(voucher);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                logger.warn(e.getMessage());
            }

        } else if (menu == LIST.getNumber()) {
            List<Voucher> voucherList = voucherService.getVoucherList();

            voucherList.stream()
                    .forEach(voucher -> System.out.println(voucher.toString()));
        }

    }
}
