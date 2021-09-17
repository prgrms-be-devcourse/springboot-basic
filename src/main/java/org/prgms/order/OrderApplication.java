package org.prgms.order;

import org.prgms.order.configuration.YamlPropertiesFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {
        "org.prgms.order.order.*",
        "org.prgms.order.customer.*",
        "org.prgms.order.voucher.*",
        "org.prgms.order.wallet",
        "org.prgms.order.configuration"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);


//        Console console = new Console();
//        new CommandLineApplication(console, console).run();
    }

}
