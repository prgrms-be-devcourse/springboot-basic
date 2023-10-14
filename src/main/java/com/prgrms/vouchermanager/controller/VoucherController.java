package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.console.Command;
import com.prgrms.vouchermanager.console.ConsolePrint;
import com.prgrms.vouchermanager.console.VoucherType;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.exception.NotCorrectCommand;
import com.prgrms.vouchermanager.exception.NotCorrectForm;
import com.prgrms.vouchermanager.exception.NotCorrectScope;
import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

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
                log.info(LogMessage.CREATE_START.getMessage());

                VoucherType voucherType = consolePrint.getVoucherType();
                long discount = consolePrint.getVoucherDiscount(voucherType);

                log.info(LogMessage.VOUCHER_INFO.getMessage(), voucherType.getType(), discount);

                service.create(voucherType, discount);
                consolePrint.printCompleteCreate();
            } else if(command == Command.LIST) {
                log.info(LogMessage.LIST_START.getMessage());

                List<Voucher> vouchers = service.list();
                if(vouchers.isEmpty()) consolePrint.printListEmpty();
                else consolePrint.printList(service.list());
            } else if(command == Command.EXIT) {
                log.info(LogMessage.FINISH_PROGRAM.getMessage());

                System.exit(0);
            }
        } catch (NotCorrectForm e) {
            System.out.println(ConsoleMessage.NOT_CORRECT_FORM.getMessage());
        } catch (NotCorrectScope e) {
            System.out.println(ConsoleMessage.NOT_CORRECT_SCOPE.getMessage());
        } catch(NotCorrectCommand e) {
            System.out.println(ConsoleMessage.NOT_CORRECT_COMMAND.getMessage());
        }
        this.run();
    }
}
