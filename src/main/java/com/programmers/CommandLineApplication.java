package com.programmers;

import com.programmers.io.Output;
import com.programmers.io.Input;
import com.programmers.voucher.Voucher;
import com.programmers.voucher.VoucherController;
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

                output.printSelectDiscount(typeOfVoucher);
                long discount = Long.parseLong(input.input());
                voucherController.createByType(typeOfVoucher, discount);
            }
            case LIST -> {
                Map<UUID, Voucher> history = voucherController.findAll();
                output.printStorage(history);
            }

            case  EXIT -> {
                applicationManager.setRun(false);
                output.printTermination();
            }
        }

    }
}
