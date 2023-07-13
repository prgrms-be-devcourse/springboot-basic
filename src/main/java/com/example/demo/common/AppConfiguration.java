package com.example.demo.common;

import com.example.demo.common.io.impl.ConsoleInput;
import com.example.demo.common.io.impl.ConsoleOutput;
import com.example.demo.common.io.Input;
import com.example.demo.common.io.Output;
import com.example.demo.customer.presentation.dto.BlackCustomer;
import com.example.demo.voucher.infrastructure.VoucherInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Configuration
@ComponentScan(basePackages = "com.example.demo")
public class AppConfiguration {


    @Bean
    @Profile("dev")
    public VoucherInfo voucherInfo(@Value("${file.path}") String filePath) throws IOException {
        VoucherInfo voucherInfo = new VoucherInfo(new ArrayList<>());
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                UUID id = UUID.fromString(parts[1]);
                long value = Long.parseLong(parts[2]);

                voucherInfo.add(name, id, value);
            }
        }
        return voucherInfo;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yaml"), new ClassPathResource("application-dev.yaml"));

        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());

        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public Input consoleInput() {
        return new ConsoleInput();
    }

    @Bean
    public Output consoleOutput() {
        return new ConsoleOutput();
    }

    @Bean
    public Set<BlackCustomer> blackList(@Value("${black.path}") String filePath) {
        Set<BlackCustomer> blackList = new HashSet<>();

        Path path = Path.of(filePath);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                blackList.add(BlackCustomer.fromCSV(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load blacklist", e);
        }

        return blackList;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3307/spring_basic");
        dataSource.setUsername("root");
        dataSource.setPassword("1234");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}