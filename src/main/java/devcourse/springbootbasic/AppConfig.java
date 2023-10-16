package devcourse.springbootbasic;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.console.input.ConsoleInput;
import devcourse.springbootbasic.commandline.console.input.ScannerInput;
import devcourse.springbootbasic.commandline.console.output.ConsoleOutput;
import devcourse.springbootbasic.commandline.console.output.PrintStreamOutput;
import devcourse.springbootbasic.domain.user.User;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.util.CsvFileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ConsoleIOHandler consoleIOHandler(ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        return new ConsoleIOHandler(consoleInput, consoleOutput);
    }

    @Bean
    public ConsoleInput consoleInput() {
        return new ScannerInput();
    }

    @Bean
    public ConsoleOutput consoleOutput() {
        return new PrintStreamOutput();
    }

    @Bean(name = "voucherCsvFileHandler")
    public CsvFileHandler<Voucher> voucherCsvFileHandler() {
        return new CsvFileHandler<>("src/main/resources/voucher.csv");
    }

    @Bean(name = "customerCsvFileHandler")
    public CsvFileHandler<User> userCsvFileHandler() {
        return new CsvFileHandler<>("src/main/resources/customer.csv");
    }
}
