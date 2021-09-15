package org.programmers.kdt;

import org.programmers.kdt.command.Command;
import org.programmers.kdt.io.Input;
import org.programmers.kdt.io.Output;
import org.programmers.kdt.utils.DigitUtils;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherType;
import org.programmers.kdt.voucher.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Component
public class CommandLineApplication implements Runnable {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    private final String requestCommandMessage
            = MessageFormat.format(
                    """
                    === Voucher Program ===
                    [Type {0} to exit the program]
                    Type {1} to create a voucher.
                    Type {2} to list all voucher.
                    Type {3} to enter customer managing page.
                    """,
            Command.EXIT, Command.CREATE, Command.LIST, Command.CUSTOMER);

    private final String requestVoucherTypeMessage
            = MessageFormat.format("Choose voucher type : {0}", List.of(VoucherType.values()));

    public CommandLineApplication(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("local");
        applicationContext.refresh();

        Command command = Command.getCommandFromInput(requestCommandMessage, Command.getVoucherApplicationCommand(), input, output);

        while (Command.EXIT != command) {
            switch (command) {
                case CREATE -> {
                    VoucherType voucherType = getVoucherType();
                    long discount = getDiscountInput();

                    Voucher voucher = createVoucher(voucherType, discount);

                    printSuccessMessage();
                    print(getPrintFormat(voucher));
                }
                case LIST -> {
                    List<Voucher> allVoucher = getAllVouchers();
                    for (Voucher voucher : allVoucher) {
                        print(getPrintFormat(voucher));
                    }
                }
                case CUSTOMER -> {
                    // Enter Customer-Voucher Managing Application
                    CustomerVoucherManagingApplication customerVoucherManagingApplication = applicationContext.getBean(CustomerVoucherManagingApplication.class);
                    customerVoucherManagingApplication.run();
                }
            }
        }
        sayGoodBye();
    }

    private long getDiscountInput() {
        String discount = input("How much of a discount do you want to give?");
        while (!DigitUtils.isDigits(discount)) {
            inputError("Invalid Input. Only digits are allowed.");
            discount = input("How much of a discount do you want to give?");
        }
        return Long.parseLong(discount);
    }

    private List<Voucher> getAllVouchers() {
        return voucherService.getAllVouchers();
    }

    private String input(String messageToPrint) {
        return input.input(messageToPrint);
    }

    private void inputError(String errorMessage) {
        output.inputError(errorMessage);
    }

    private void printSuccessMessage() {
        output.printSuccessMessage();
    }

    private void sayGoodBye() {
        output.sayGoodBye();
    }

    private void print(String message) {
        output.print(message);
    }

    private String getPrintFormat(Voucher voucher) {
        return voucherService.getPrintFormat(voucher);
    }

    private Voucher createVoucher(VoucherType voucherType, long discount) {
        try {
            return voucherService.createVoucher(voucherType, UUID.randomUUID(), discount);
        } catch (RuntimeException e) {
            output.inputError(e.getMessage());
            throw new RuntimeException("Cannot create voucher!", e);
        }
    }

    private VoucherType getVoucherType() {
        try {
            return VoucherType.of(input.input(requestVoucherTypeMessage));
        } catch (RuntimeException e) {
            output.inputError(MessageFormat.format("Invalid Input. You can create {0} discount voucher", List.of(VoucherType.values())));
            throw new RuntimeException("Invalid Input", e);
        }
    }
}
