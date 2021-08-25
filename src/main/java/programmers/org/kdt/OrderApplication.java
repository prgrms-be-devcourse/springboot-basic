package programmers.org.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import programmers.org.kdt.configuration.YamlPropertiesFactory;
import programmers.org.kdt.engine.order.OrderProperties;

//@SpringBootApplication
@ComponentScan(basePackages = {"programmers.org.kdt.engine", "programmers.org.kdt.configuration"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
public class OrderApplication {

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) {

        var springApplication = new SpringApplication(OrderApplication.class);
        springApplication.setAdditionalProfiles("default");
        var applicationContext = springApplication.run(args);
        var orderProperties = applicationContext.getBean(OrderProperties.class);

        logger.info("version -> {}", orderProperties.getVersion());
        logger.info("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
        logger.info("supportVendors -> {}", orderProperties.getSupportVendors());
        logger.info("description -> {}", orderProperties.getDescription());

    }

}
