package devcourse.springbootbasic.config;

import devcourse.springbootbasic.commandline.console.ConsoleIOHandler;
import devcourse.springbootbasic.commandline.console.input.ConsoleInput;
import devcourse.springbootbasic.commandline.console.input.ScannerInput;
import devcourse.springbootbasic.commandline.console.output.ConsoleOutput;
import devcourse.springbootbasic.commandline.console.output.PrintStreamOutput;
import devcourse.springbootbasic.util.CsvFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${spring.myapp.file-path.voucher}")
    private String voucherFilePath;
    @Value("${spring.myapp.file-path.customer}")
    private String customerFilePath;

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
    public CsvFileHandler voucherCsvFileHandler() {
        return new CsvFileHandler(voucherFilePath);
    }

    @Bean(name = "customerCsvFileHandler")
    public CsvFileHandler customerCsvFileHandler() {
        return new CsvFileHandler(customerFilePath);
    }
}
