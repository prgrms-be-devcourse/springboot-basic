package org.prgrms.kdt.config;

import org.prgrms.kdt.CommandLineApplication;
import org.prgrms.kdt.KdtApplication;
import org.prgrms.kdt.io.console.ConsoleIO;
import org.prgrms.kdt.io.file.CsvFileIO;
import org.prgrms.kdt.io.file.IO;
import org.prgrms.kdt.io.file.TxtFileIO;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@ComponentScan(basePackageClasses = {CommandLineApplication.class})
@PropertySource(value = "application.yml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

    private static final String TXT_PATH = "db.txt";
    private static final String CSV_PATH = "customer_blacklist.csv";

    @Bean
    public IO txtFileIo() throws IOException {
        return new TxtFileIO(TXT_PATH);
    }

    @Bean
    public IO csvFileIo() throws IOException {
        return new CsvFileIO(CSV_PATH);
    }

    @Bean
    public IO consoleIo() {
        return new ConsoleIO();
    }

}

