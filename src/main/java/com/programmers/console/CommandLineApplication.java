package com.programmers.console;

import com.programmers.console.util.Command;
import com.programmers.console.util.VoucherStringSerializer;
import com.programmers.console.view.Console;
import com.programmers.voucher.controller.VoucherController;
import com.programmers.voucher.domain.DiscountType;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("dev")
@Component
public class CommandLineApplication implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private static final String WRONG_INPUT_MESSAGE_FOR_VALUE = "[ERROR] 숫자를 입력해 주세요.";
    private static final String LINE_CHANGER = "\n";

    private final VoucherController voucherController;
    private final Console console;

    private boolean isRunning = true;

    public CommandLineApplication(Console console, VoucherController voucherController) {
        this.console = console;
        this.voucherController = voucherController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
        DiscountType discountType = inputDiscountTypeInfo();
        long discountValue = inputDiscountValueInfo();
        return new VoucherRequestDto(discountType, discountValue);
    }

    private DiscountType inputDiscountTypeInfo() {
        while (true) {
            try {
                return DiscountType.of(console.inputDiscountType());
            } catch (IllegalArgumentException e) {
                console.println(e.getMessage());
                logger.error("Log Message -> {}", e.getMessage());
            }
        }
    }

    private long inputDiscountValueInfo() {
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
        List<VoucherResponseDto> vouchers = voucherController.findAll();
        printVouchers(vouchers);
    }

    private void printVoucher(VoucherResponseDto responseDto) {
        console.println(VoucherStringSerializer.convertVoucherResponseToString(responseDto) + LINE_CHANGER);
    }

    private void printVouchers(List<VoucherResponseDto> vouchers) {
        StringBuilder result = new StringBuilder();
        for (VoucherResponseDto voucher : vouchers) {
            result.append(VoucherStringSerializer.convertVoucherResponseToString(voucher) + LINE_CHANGER + LINE_CHANGER);
        }
        console.println(result);
    }
}
