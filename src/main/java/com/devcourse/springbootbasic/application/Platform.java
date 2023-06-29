package com.devcourse.springbootbasic.application;

import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.Menu;
import com.devcourse.springbootbasic.application.exception.InvalidDataException;
import com.devcourse.springbootbasic.application.io.InputConsole;
import com.devcourse.springbootbasic.application.io.OutputConsole;
import com.devcourse.springbootbasic.application.service.CustomerService;
import com.devcourse.springbootbasic.application.service.VoucherService;
import com.devcourse.springbootbasic.application.template.CommandLineTemplate;
import com.devcourse.springbootbasic.application.template.CreateMenuTemplate;
import com.devcourse.springbootbasic.application.template.ListMenuTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Platform {

    private static final Logger logger = LoggerFactory.getLogger(Platform.class);

    private final CommandLineTemplate commandLineTemplate;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public Platform(
            InputConsole input, OutputConsole output,
            VoucherService voucherService, CustomerService customerService
    ) {
        this.commandLineTemplate = new CommandLineTemplate(input, output);
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    public void run() {
        logger.debug("Program Started");
        while (true) {
            if (isEndGame()) {
                logger.debug("Program Terminated");
                break;
            }
        }
    }

    private boolean isEndGame() {
        try {
            return branchByMenu(commandLineTemplate.menuTask());
        } catch (InvalidDataException e) {
            commandLineTemplate.errorTask(e);
            logger.error(e.getMessage(), e.getCause());
        }
        return false;
    }

    private boolean branchByMenu(Menu menu) {
        return switch (menu) {
            case EXIT -> {
                commandLineTemplate.endGameTask();
                yield true;
            }
            case CREATE -> {
                createVoucherTask();
                yield false;
            }
            case LIST -> {
                listMenuTask();
                yield false;
            }
        };
    }

    private void createVoucherTask() {
        Voucher voucher = new CreateMenuTemplate(voucherService).createTask(
                commandLineTemplate.createVoucherDto()
        );
        commandLineTemplate.printVoucher(voucher);
    }

    private void listMenuTask() {
        List<String> list = new ListMenuTemplate(customerService, voucherService).listTask(
                commandLineTemplate.listTask()
        );
        commandLineTemplate.printVouchers(list);
    }

}
