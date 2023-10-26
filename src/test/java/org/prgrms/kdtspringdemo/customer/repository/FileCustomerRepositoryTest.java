package org.prgrms.kdtspringdemo.customer.repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.file.CsvFileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("dev")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileCustomerRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.kdtspringdemo"})
    static class Config {
    }
    @Autowired
    private FileCustomerRepository fileCustomerRepository;
    private String filePath = "src/main/resources/csvFiles/customer_blacklist.csv";
    private final Logger logger = LoggerFactory.getLogger(FileCustomerRepositoryTest.class);

    @BeforeAll
    void init() throws IOException {
        try(FileWriter fileWriter = new FileWriter(filePath);
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("customerId", "name", "isBlack"));) {
            csvPrinter.printRecord(UUID.randomUUID().toString(), "tester01", "true");
            csvPrinter.printRecord(UUID.randomUUID().toString(), "tester02", "true");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    @Test
    @DisplayName("블랙리스트 고객 리스트를 확인합니다.")
    void getAllBlackList() throws IOException {
        //when
        List customerMap = fileCustomerRepository.getAllBlackList().get();

        //then
        assertThat(customerMap.size(), is(2));
    }
}