package com.programmers.console;

import com.programmers.console.util.Command;
import com.programmers.console.util.VoucherStringSerializer;
import com.programmers.console.view.Console;
import com.programmers.global.aop.LoggingAspect;
import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.domain.Discount;
import com.programmers.voucher.domain.VoucherType;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CommandLineApplication {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private static final String WRONG_INPUT_MESSAGE_FOR_VALUE = "[ERROR] 숫자를 입력해 주세요.";
    private static final String EMPTY_SPACE = "";

    private final VoucherController voucherController;
    private final Console console;

    private boolean isRunning = true;

    public CommandLineApplication(Console console, VoucherController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
    }

    public void run() {
        while (isRunning) {
            try {
                String inputMenuText = console.inputMenu();
                Command inputCommand = Command.of(inputMenuText);
                execute(inputCommand);
            } catch (RuntimeException e) {
                console.println(e.getMessage());
                logger.error("Log Message -> {}", e.getMessage());
            }
        }
    }

    private void execute(Command command) {
        switch (command) {
            case EXIT -> isRunning = false;
            case CREATE -> create();
            case LIST -> displayVoucherList();
        }
    }

    private void create() {
        VoucherRequestDto requestDto = createRequestDtoByUserInput();
        VoucherResponseDto createdVoucherResponse = createVoucher(requestDto);
        printVoucher(createdVoucherResponse);
    }

    private VoucherRequestDto createRequestDtoByUserInput() {
        VoucherType voucherType = inputVoucherInfo();
        long discountInfo = inputDiscountInfo();
        Discount discount = Discount.of(voucherType, discountInfo);
        return new VoucherRequestDto(UUID.randomUUID(), discount);
    }

    private VoucherType inputVoucherInfo() {
        while (true) {
            try {
                return VoucherType.of(console.inputVoucherType());
            } catch (IllegalArgumentException e) {
                console.println(e.getMessage());
                logger.error("Log Message -> {}", e.getMessage());
            }
        }
    }

    private long inputDiscountInfo() {
        while (true) {
            try {
                return Long.parseLong(console.inputDiscountValue());
            } catch (NumberFormatException e) {
                console.println(WRONG_INPUT_MESSAGE_FOR_VALUE);
                logger.error("Log Message -> {}", e.getMessage());
            }
        }
    }

    private VoucherResponseDto createVoucher(VoucherRequestDto requestDto) {
        return voucherController.create(requestDto);
    }

    private void displayVoucherList() {
        printVouchers(voucherController.findAll());
    }

    private void printVoucher(VoucherResponseDto responseDto) {
        console.println(VoucherStringSerializer.convertVoucherResponseToString(responseDto));
        console.println(EMPTY_SPACE);
    }

    private void printVouchers(List<VoucherResponseDto> vouchers) {
        for (VoucherResponseDto voucher : vouchers) {
            printVoucher(voucher);
        }
    }
}
