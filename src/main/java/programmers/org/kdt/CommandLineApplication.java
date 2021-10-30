package programmers.org.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import programmers.org.kdt.engine.CommandLine;
import programmers.org.kdt.engine.customer.CustomerServiceImpl;
import programmers.org.kdt.engine.io.BlackList;
import programmers.org.kdt.engine.io.ConsoleIO;
import programmers.org.kdt.engine.io.ConsoleTerminal;
import programmers.org.kdt.engine.voucher.VoucherProperties;
import programmers.org.kdt.engine.voucher.VoucherService;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class CommandLineApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    public static void main(String[] args) {
        // Console
        ConsoleIO console = new ConsoleTerminal();

        // Spring Application
        var springApplication = new SpringApplication(CommandLineApplication.class);
        springApplication.setAdditionalProfiles("local");
        var applicationContext = springApplication.run(args);
        var voucherProperties = applicationContext.getBean(VoucherProperties.class);

        // VoucherService
        var voucherService = applicationContext.getBean(VoucherService.class);
        logger.info("version -> {}", voucherProperties.getVersion());

        // CustomerService
        var customerService =
            applicationContext.getBean(CustomerServiceImpl.class);

        // Get BlackList
        try {
            logger.info("blackListFile -> {}", voucherProperties.getBlackListFile());
            var blackList = new BlackList().getBlackList(voucherProperties.getBlackListFile());
            logger.info("blackList -> {}", blackList);
        } catch (BeansException e) {
            e.printStackTrace();
            console.close();
            applicationContext.close();
            return;
        }

        // run
        var commandLine = new CommandLine(console, voucherService, customerService);
        logger.info("Start of CommandLineApplication");
        commandLine.run();

        // close
        console.close();
        applicationContext.close();
        logger.info("End of CommandLineApplication");
    }
}
