package com.programmers;

import com.programmers.io.Output;
import com.programmers.io.Input;
import com.programmers.voucher.Voucher;
import com.programmers.voucher.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;



@Component
public class CommandLineApplication {

    private final Output output;
    private final Input input;
    private final VoucherController voucherController;
    private final ApplicationManager applicationManager = new ApplicationManager(true);

    private final String pattern = "[0-9]*";

    private final static Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public CommandLineApplication(Output output, Input input, VoucherController voucherController) {
        this.output = output;
        this.input = input;
        this.voucherController = voucherController;
    }

    public static void main(String[] args) throws IOException {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.getBean(CommandLineApplication.class).run();
    }

    public void run() throws IOException {
        output.printDescription();
        while (applicationManager.isRun()) {
            output.printStartOrder();
            String command = input.input();
            Command inputCommand= Command.getCommand(command);
            executeByCommand(inputCommand);
        }
    }

    private void executeByCommand(Command inputCommand) throws IOException {
        switch (inputCommand) {
            case CREATE -> {
                output.printSelectVoucher();
                String typeNumber = input.input();
                TypeOfVoucher typeOfVoucher = TypeOfVoucher.getType(typeNumber);
                if(typeOfVoucher==TypeOfVoucher.ERROR_VOUCHER) {
                    logger.error("존재하지 않는 바우처입니다.");
                    break;
                }

                output.printSelectDiscount(typeOfVoucher);
                String inputDiscount = input.input();
                if (!inputDiscount.matches(pattern) || inputDiscount.isEmpty()) {
                    logger.error("할인금액 또는 할인율이 숫자가 아닙니다.");
                    break;
                }
                long discount = Long.parseLong(inputDiscount);

                try {
                    voucherController.createByType(typeOfVoucher, discount);
                } catch (IllegalArgumentException e) {
                    logger.error("할인금액 또는 할인율이 정상 범위가 아닙니다.");
                }
            }
            case LIST -> {
                Map<UUID, Voucher> history = voucherController.findAll();
                output.printStorage(history);
            }

            case  EXIT -> {
                applicationManager.setRun(false);
                output.printTermination();
                logger.info("application 이 정상적으로 종료되었습니다.");
            }
            case ERROR -> logger.error("잘못된 명령입니다.");
        }

    }
}
