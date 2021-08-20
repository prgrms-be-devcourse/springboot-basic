package org.prgrms.kdt;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.prgrms.kdt.controller.VoucherController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = {"org.prgrms.kdt.controller",
        "org.prgrms.kdt.io",
        "org.prgrms.kdt.repository",
        "org.prgrms.kdt.service",
        "org.prgrms.kdt.fileutil",
        "org.prgrms.kdt.factory"
})
@PropertySource(value="application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class CommandLineApplication {

    public static void main(String[] args) {

        VoucherController voucherController = new VoucherController();
        voucherController.run(new AnnotationConfigApplicationContext(CommandLineApplication.class));

    }
}
