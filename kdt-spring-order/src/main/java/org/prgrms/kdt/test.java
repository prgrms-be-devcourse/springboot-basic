package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) {
        var annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var environment = annotationConfigApplicationContext.getEnvironment();
        //var version = environment.getProperty("kdt.version");
        //System.out.println("version" + version);
        environment.setActiveProfiles("dev");
        annotationConfigApplicationContext.getResource("customer_blacklist.csv");
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        logger.info("logger name => {}", logger.getName());
        var orderProperties = new OrderProperties();
    }
}
