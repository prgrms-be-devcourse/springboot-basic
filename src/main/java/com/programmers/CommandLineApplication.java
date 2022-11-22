package com.programmers;

import com.programmers.io.Output;
import com.programmers.io.Input;
import com.programmers.voucher.domain.TypeOfVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.service.VoucherCommandLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;



@Component
public class CommandLineApplication {

    private final Output output;
    private final Input input;
    private final VoucherCommandLineService voucherCommandLineService;
    private final ApplicationManager applicationManager = new ApplicationManager(true);


    private final static Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(Output output, Input input, VoucherCommandLineService voucherCommandLineService) {
        this.output = output;
        this.input = input;
        this.voucherCommandLineService = voucherCommandLineService;
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
                Map<UUID, Voucher> history = voucherCommandLineService.findAll();
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
        String typeNumber = input.input();
        try {
            TypeOfVoucher typeOfVoucher = TypeOfVoucher.getType(typeNumber);
            output.printSelectDiscount(TypeOfVoucher.getGuideMessage(typeOfVoucher));
            long inputDiscount = input.inputNumber();
            Voucher voucher = TypeOfVoucher.createVoucher(typeOfVoucher, inputDiscount);
            voucherCommandLineService.saveVoucher(voucher);
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
