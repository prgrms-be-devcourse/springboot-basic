package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.io.Command;
import com.prgrms.vouchermanager.io.ConsolePrint;
import com.prgrms.vouchermanager.io.VoucherType;
import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.exception.NotCorrectCommand;
import com.prgrms.vouchermanager.exception.NotCorrectForm;
import com.prgrms.vouchermanager.exception.NotCorrectScope;
import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
@Slf4j
public class VoucherController {
    private final ConsolePrint consolePrint;
    private final VoucherService service;

    public VoucherController(ConsolePrint consolePrint, VoucherService service) {
        this.consolePrint = consolePrint;
        this.service = service;
    }

    public void run() {
        try {
            Command command = consolePrint.run();

            if(command == Command.CREATE) {
                VoucherType voucherType = consolePrint.getVoucherType();
                long discount = consolePrint.getVoucherDiscount(voucherType);

                log.info(LogMessage.VOUCHER_TYPE_AND_DISCOUNT.getMessage(), voucherType.getType(), discount);

                service.create(voucherType, discount);
                consolePrint.printCompleteCreate();
            } else if(command == Command.LIST) {
                List<Voucher> vouchers = service.list();
                if(vouchers.isEmpty()) throw new EmptyListException();
                else consolePrint.printList(service.list());
            } else if(command == Command.EXIT) {
                log.info(LogMessage.FINISH_PROGRAM.getMessage());

                System.exit(0);
            } else if(command == Command.BLACKLIST) {
                List<Customer> customers = service.blacklist();
                if(customers.isEmpty()) throw new EmptyListException();
                else consolePrint.printBlacklist(service.blacklist());
            }
        } catch (NotCorrectForm | NotCorrectScope | NotCorrectCommand | EmptyListException e) {
            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        this.run();
    }
}
