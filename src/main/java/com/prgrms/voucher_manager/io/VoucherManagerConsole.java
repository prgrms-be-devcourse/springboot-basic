package com.prgrms.voucher_manager.io;

import com.prgrms.voucher_manager.customer.CustomerService;
import com.prgrms.voucher_manager.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VoucherManagerConsole {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherManagerConsole.class);

    public VoucherManagerConsole(Input input, Output output, VoucherService voucherService, CustomerService customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run() throws IOException {
        while(true){
            output.consoleMenu();
            String select = input.selectOption();
            try{
                Command command = Command.getCommand(select);
                switch (command){
                    case CREATE:
                        output.selectVoucher();
                        String voucherType = input.selectOption();
                        long value = Long.parseLong(input.input("원하는 할인 양을 입력해주세요. "));
                        voucherService.createVoucher(voucherType, value);
                        break;
                    case LIST:
                        voucherService.getFindAllVoucher();
                        break;
                    case BLACKLIST:
                        customerService.findAll();
                        break;
                    case EXIT:
                        output.exitProgram();
                        System.exit(0);
                    default:
                        logger.info("wrong input {}",command);
                        output.wrongInput();
                        break;
                }
            }catch (IllegalArgumentException e){
                logger.info("console Menu wrong input : {}",select);
            }
        }
    }

}
