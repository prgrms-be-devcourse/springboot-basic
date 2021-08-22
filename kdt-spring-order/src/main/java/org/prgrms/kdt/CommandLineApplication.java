package org.prgrms.kdt;

import org.prgrms.kdt.configuration.YamlPropertiesFactory;
import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.service.OrderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

//@ComponentScan( basePackages = { "org.prgrms.kdt.controller"},
//        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = VoucherController.class)
//)


//@ComponentScan(basePackages = {"org.prgrms.kdt.controller",
//        "org.prgrms.kdt.io",
//        "org.prgrms.kdt.repository",
//        "org.prgrms.kdt.service",
//        "org.prgrms.kdt.fileutil",
//        "org.prgrms.kdt.factory"
//},
//        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = OrderService.class)
//)
//@PropertySource(value="application.yaml", factory = YamlPropertiesFactory.class)
@SpringBootApplication
@PropertySource("classpath:/application.yaml")
@EnableConfigurationProperties
public class CommandLineApplication {

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

        var applicationContext = SpringApplication.run(CommandLineApplication.class,args);
        VoucherController voucherController = new VoucherController();
        voucherController.run();
        applicationContext.close();

    }
}
