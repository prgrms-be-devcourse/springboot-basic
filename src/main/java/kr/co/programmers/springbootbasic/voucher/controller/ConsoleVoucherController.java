package kr.co.programmers.springbootbasic.voucher.controller;

import kr.co.programmers.springbootbasic.io.Input;
import kr.co.programmers.springbootbasic.io.Output;
import kr.co.programmers.springbootbasic.io.enums.VoucherServiceCommand;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@Profile("console")
public class ConsoleVoucherController {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleVoucherController.class);
    private final Input inputConsole;
    private final Output outputConsole;
    private final VoucherService voucherService;

    public ConsoleVoucherController(Input inputConsole, Output outputConsole, VoucherService voucherService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherService = voucherService;
    }

    public void doVoucherService() {
        outputConsole.printVoucherServiceMenu();
        VoucherServiceCommand command = inputConsole.readVoucherCommand();
        switch (command) {
            case CREATE_VOUCHER -> createVoucher();
            case LIST_ALL_VOUCHERS -> listAllVoucher();
        }
    }

    private void createVoucher() {
        outputConsole.printVoucherCreateMenu();
        VoucherType type = inputConsole.readVoucherType();

        outputConsole.printAmountEnterMessage(type);
        long amount = inputConsole.readAmount();

        VoucherResponse voucherDto = voucherService.createVoucher(type, amount);
        outputConsole.printVoucherMessage(voucherDto);
    }

    private void listAllVoucher() {
        List<VoucherResponse> voucherDtos = voucherService.listAllVoucher();
        outputConsole.printVoucherListMessage(voucherDtos);
    }

    private void deleteById(UUID voucherId) {
        voucherService.deleteById(voucherId);
    }
}
