package kr.co.programmers.springbootbasic;

import kr.co.programmers.springbootbasic.customer.dto.CustomerDto;
import kr.co.programmers.springbootbasic.customer.service.CustomerService;
import kr.co.programmers.springbootbasic.io.Input;
import kr.co.programmers.springbootbasic.io.MenuCommand;
import kr.co.programmers.springbootbasic.io.Output;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherCreationRequestDto;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherDto;
import kr.co.programmers.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Processor implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(Processor.class);
    private final Input inputConsole;
    private final Output outputConsole;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private boolean isExit;

    public Processor(Input inputConsole, Output outputConsole, VoucherService voucherService, CustomerService userService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherService = voucherService;
        this.customerService = userService;
        this.isExit = false;
    }

    @Override
    public void run(ApplicationArguments args) {
        while (!isExit) {
            executeServiceLoop();
        }
        logger.info("서비스를 종료합니다.");
        outputConsole.printExit();
    }

    private void executeServiceLoop() {
        try {
            doService();
        } catch (RuntimeException e) {
            outputConsole.printMessage(e.getMessage());
        }
    }

    private void doService() throws RuntimeException {
        outputConsole.printProgramMenu();
        MenuCommand menuCommand = inputConsole.readMenuCommand();
        switch (menuCommand) {
            case EXIT -> isExit = true;
            case CREATE -> createVoucher();
            case LIST -> listAllVoucher();
            case BLACK_LIST -> listAllBlackCustomer();
        }
    }

    private void createVoucher() throws RuntimeException {
        outputConsole.printCreationMenu();
        VoucherType type = inputConsole.readVoucherType();

        outputConsole.printAmountEnterMessage(type);
        long amount = inputConsole.readAmount();
        VoucherCreationRequestDto voucherCreationRequestDto = new VoucherCreationRequestDto(type, amount);
        VoucherDto voucherDto = voucherService.createVoucher(voucherCreationRequestDto);

        outputConsole.printVoucherMessage(voucherDto);
    }

    private void listAllVoucher() throws RuntimeException {
        List<VoucherDto> voucherDtos = voucherService.listAllVoucher();
        outputConsole.printVoucherListMessage(voucherDtos);
    }

    private void listAllBlackCustomer() {
        List<CustomerDto> customerResponseDtos = customerService.listAllBlackCustomer();
        outputConsole.printCustomerListMessage(customerResponseDtos);
    }
}
