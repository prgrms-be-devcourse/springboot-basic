package com.programmers.springweekly.controller;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherMenu;
import com.programmers.springweekly.service.VoucherService;
import com.programmers.springweekly.util.Validator;
import com.programmers.springweekly.view.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.UUID;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;
    private final Console console;

    public VoucherController(VoucherService voucherService, Console console){
        this.voucherService = voucherService;
        this.console = console;
    }

    public void createVoucher(){
        console.outputSelectCreateVoucherGuide();

        try {
            VoucherMenu voucherMenu = VoucherMenu.findVoucherMenu(console.inputMessage());

            console.outputDiscountGuide();
            String inputNumber = console.inputMessage();
            logger.info("user input: {} ", inputNumber);

            if(voucherMenu == VoucherMenu.FIXED){
                Validator.fixedAmountValidate(inputNumber);
            }

            if(voucherMenu == VoucherMenu.PERCENT){
                Validator.percentValidate(inputNumber);
            }

            voucherService.saveVoucher(voucherMenu, Long.parseLong(inputNumber));
        } catch (IllegalArgumentException e){
            logger.error(e.getMessage());
        }
    }

    public void getVoucherList(){
        Map<UUID, Voucher> voucherMap= voucherService.findVoucherAll();

        if(voucherMap.isEmpty()){
            console.outputErrorMessage("No vouchers saved");
            return;
        }

        console.outputGetVoucherAll(voucherMap);
    }
}
