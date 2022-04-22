package com.programmers.part1;


import com.programmers.part1.customer.CustomerService;
import com.programmers.part1.io.Console;
import com.programmers.part1.order.voucher.VoucherService;
import com.programmers.part1.ui.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CommandLineApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CommandLineApplication.class, args);
        Client client = Client.builder()
                .console(new Console())
                .customerService(applicationContext.getBean(CustomerService.class))
                .voucherService(applicationContext.getBean(VoucherService.class))
                .build();
        client.run();
    }
}
