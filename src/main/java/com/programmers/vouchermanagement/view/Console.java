package com.programmers.vouchermanagement.view;

import com.programmers.vouchermanagement.VoucherManagementApplication;
import com.programmers.vouchermanagement.common.ConsoleMessage;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.repository.customer.CustomerRepository;
import com.programmers.vouchermanagement.service.VoucherService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Component
public class Console implements CommandLineRunner {

    private final VoucherService voucherService;
    private final CustomerRepository costumerRepository;
    private final Map<String, Runnable> commandMap = new HashMap<>();
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final Logger logger = LoggerFactory.getLogger(VoucherManagementApplication.class);
    private final int VOUCHER_NAME_MIN_LENGTH = 1;
    private final float DISCOUNT_AMOUNT_MIN_VALUE = 0f;

    public Console(VoucherService voucherService, CustomerRepository costumerRepository) {
        this.voucherService = voucherService;
        this.costumerRepository = costumerRepository;
    }

    @PostConstruct
    private void init() {
        commandMap.put("create", this::createVoucher);
        commandMap.put("list", this::displayVoucherList);
        commandMap.put("blacklist", this::displayCustomerBlackList);
        commandMap.put("exit", this::exit);
    }

    @Override
    public void run(String... args) {
        logger.info("VoucherManagementApplication started");
        while (true) {
            commandMap.get(getCommand()).run();
        }
    }

    private String getCommand() {
        displayMessage(ConsoleMessage.COMMAND_LIST_MESSAGE.getMessage());
        Command command = textIO.newEnumInputReader(Command.class)
                .withPossibleValues()
                .read(">");
        logger.info(MessageFormat.format("Input Command: {0}", command));
        return command.toString();
    }

    private void createVoucher() {
        displayMessage(ConsoleMessage.CHOICE_VOUCHER_TYPE_MESSAGE.getMessage());
        VoucherType voucherType = textIO.newEnumInputReader(VoucherType.class)
                .withAllValues()
                .read();

        displayMessage(voucherType.getGuideMessage());
        String voucherName = textIO.newStringInputReader()
                .withMinLength(VOUCHER_NAME_MIN_LENGTH)
                .read("Voucher Name: ");
        float discountAmount = textIO.newFloatInputReader()
                .withMinVal(DISCOUNT_AMOUNT_MIN_VALUE)
                .read("Discount Amount: ");

        voucherService.createVoucher(voucherName, discountAmount, voucherType);
        displayMessage(ConsoleMessage.VOUCHER_CREATED_MESSAGE.getMessage());
    }

    private void displayVoucherList() {
        voucherService.voucherList()
                .forEach(voucher -> displayMessage(voucher.toString()));
    }

    private void displayCustomerBlackList() {
        costumerRepository.findAllBannedCustomers()
                .forEach(customer -> displayMessage(customer.toString()));
    }

    private void exit() {
        displayMessage(ConsoleMessage.EXIT_MESSAGE.getMessage());
        System.exit(0);
    }

    private void displayMessage(String message) {
        textIO.getTextTerminal().println(message);
    }

    @PreDestroy
    private void destroy() {
        logger.info("VoucherManagementApplication stopped");
    }
}
