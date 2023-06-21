package com.example.demo.voucher.presentation;

import com.example.demo.common.AppConfiguration;
import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import com.example.demo.voucher.application.VoucherCommand;
import com.example.demo.voucher.application.VoucherService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherRunner {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var voucherService = applicationContext.getBean(VoucherService.class);
        var input = applicationContext.getBean(Input.class);
        var output = applicationContext.getBean(Output.class);

        String command = "";

        while (!command.equals("exit")) {
            output.printLine("=== Voucher Program ===");
            output.printLine("Type exit to exit the program.");
            output.printLine("Type create to create a new voucher.");
            output.printLine("Type list to list all vouchers.");

            command = input.readLine();

            try {
                VoucherCommand strategy = applicationContext.getBean(command, VoucherCommand.class);
                strategy.execute(voucherService);
            } catch (NoSuchBeanDefinitionException ex) {
                output.printLine("Unknown command");
            }
        }
    }
}
