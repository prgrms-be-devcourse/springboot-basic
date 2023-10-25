package org.prgms.springbootbasic.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.common.file.CsvFileTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgms.springbootbasic.common.CommonConstant.CSV_PATTERN;

@Component
@Profile({"dev", "prod"})
@Slf4j
public class CustomerCsvFileManager {
    private static final String BLACK_PATH = "./src/main/resources/customer_blacklist.csv";
    private static final int UUID_IDX = 0;
    private static final int NAME_IDX = 1;
    private static final int EMAIL_IDX = 2;
    private static final int CREATED_IDX = 3;
    private static final int LAST_LOGIN_IDX = 4;

    private final CsvFileTemplate<Customer> csvFileTemplate;

    public CustomerCsvFileManager(CsvFileTemplate<Customer> csvFileTemplate) {
        this.csvFileTemplate = csvFileTemplate;
    }


    public List<Customer> readBlack(){
        return csvFileTemplate.read(BLACK_PATH, this::lineToBlack);
    }

    private Customer lineToBlack(String line){
        log.debug("line = {}", line);

        List<String> splitLine = Arrays.stream(line.split(CSV_PATTERN))
                .map(s -> s.replaceAll("\"", "")).toList();

        UUID uuid = UUID.fromString(splitLine.get(UUID_IDX));
        String name = splitLine.get(NAME_IDX);
        String email = splitLine.get(EMAIL_IDX);
        LocalDateTime createdAt = LocalDateTime.parse(splitLine.get(CREATED_IDX));
        LocalDateTime lastLoginAt = LocalDateTime.parse(splitLine.get(LAST_LOGIN_IDX));

        return new Customer(uuid, name, email, createdAt, lastLoginAt, false);
    }

}
