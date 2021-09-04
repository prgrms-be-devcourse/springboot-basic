package com.prgms.kdtspringorder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.prgms.kdtspringorder.adapter.controller.CustomerController;
import com.prgms.kdtspringorder.adapter.controller.VoucherController;
import com.prgms.kdtspringorder.adapter.exception.InvalidDiscountException;
import com.prgms.kdtspringorder.application.CustomerService;
import com.prgms.kdtspringorder.application.VoucherService;
import com.prgms.kdtspringorder.config.AppConfig;
import com.prgms.kdtspringorder.domain.model.voucher.VoucherType;
import com.prgms.kdtspringorder.ui.Command;
import com.prgms.kdtspringorder.ui.Printer;
import com.prgms.kdtspringorder.ui.Receiver;

public class CommandLineApplication {
    private static final Receiver RECEIVER = new Receiver();
    private static final Printer PRINTER = new Printer();

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);

        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.setActiveProfiles("dev");
        applicationContext.refresh();

        CustomerController customerController = new CustomerController(
            applicationContext.getBean(CustomerService.class));
        VoucherController voucherController = new VoucherController(applicationContext.getBean(VoucherService.class));

        findCustomerBlacklist(customerController);
        manageVouchers(voucherController);

        applicationContext.close();
    }

    private static void findCustomerBlacklist(CustomerController customerController) {
        PRINTER.printBlacklist(customerController.findAllBlacklist());
    }

    private static void manageVouchers(VoucherController voucherController) {
        PRINTER.printCommandList();

        while (true) {
            Command command = null;
            try {
                command = Command.valueOf(RECEIVER.enterCommand().toUpperCase());
            } catch (IllegalArgumentException e) {
                PRINTER.printInvalidCommandMessage();
                continue;
            }

            if (command.equals(Command.EXIT)) {
                break;
            }

            switch (command) {
                case CREATE:
                    createVoucher(voucherController);
                    break;
                case LIST:
                    PRINTER.printVoucherList(voucherController.listVouchers());
                    break;
                case HELP:
                    PRINTER.printCommandList();
                    break;
                default:
                    break;
            }
        }
    }

    private static void createVoucher(VoucherController voucherController) {
        try {
            VoucherType voucherType = VoucherType.valueOf(RECEIVER.enterVoucherType().toUpperCase());
            long discount = RECEIVER.enterDiscountAmount();
            voucherController.createVoucher(voucherType, discount);
        } catch (InvalidDiscountException e) {
            PRINTER.printInvalidDiscount(e.getMessage());   // 할인률이 100% 초과일 경우
        } catch (IllegalArgumentException e) {
            PRINTER.printInvalidVoucherType(e.getMessage());    // 잘못된 voucher type을 입력했을 경우
        }
    }
}
