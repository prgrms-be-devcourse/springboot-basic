package com.programmers;

import com.programmers.io.Input;
import com.programmers.io.Output;
import com.programmers.voucher.domain.TypeOfVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;



@Component
public class CommandLineApplication {

    private final Output output;
    private final Input input;
    private final VoucherService voucherService;
    private final ApplicationManager applicationManager = new ApplicationManager(true);


    private final static Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(Output output, Input input, VoucherService voucherService) {
        this.output = output;
        this.input = input;
        this.voucherService = voucherService;
    }

    public void run() throws IOException {
        output.printDescription();
        while (applicationManager.isRun()) {
            output.printStartOrder();
            String command = input.input();
            try {
                Command inputCommand = Command.getCommand(command);
                executeByCommand(inputCommand);
            } catch (RuntimeException e) {
                logger.error("잘못된 명령입니다.");
            }

        }
    }

    private void executeByCommand(Command inputCommand) throws IOException {
        switch (inputCommand) {
            case CREATE -> createVoucher();

            case LIST -> {
                List<Voucher> history = voucherService.getAllVouchers();
                logger.info("voucher 가 전체 조회되었습니다.");
                output.printStorage(history);
            }

            case EXIT -> {
                applicationManager.setRun(false);
                output.printTermination();
                logger.info("application 이 정상적으로 종료되었습니다.");
            }
        }
    }

    private void createVoucher() throws IOException {
        output.printSelectVoucher();
        String type = input.input();
        try {
            TypeOfVoucher typeOfVoucher = TypeOfVoucher.getType(type);
            output.printSelectDiscount(TypeOfVoucher.getGuideMessage(typeOfVoucher));
            long inputDiscount = input.inputNumber();
            voucherService.createVoucher("dark@gmail.com", type, inputDiscount);
        } catch (NumberFormatException e) {
            logger.error("할인금액 또는 할인율이 숫자가 아닙니다.");
        } catch (IllegalArgumentException e) {
            output.printError(e.getMessage());
            logger.error("할인금액 또는 할인율이 정상 범위가 아닙니다.");
        } catch (RuntimeException e) {
            output.printError(e.getMessage());
            logger.error("존재하지 않는 바우처입니다.");
        }
    }
}
