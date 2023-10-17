package org.prgms.springbootbasic.domain.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.prgms.springbootbasic.common.Util.CSV_PATTERN;

@Component
@Profile({"dev", "prod"})
@Slf4j
public class CsvCustomerFileManager {
    private static final String BLACK_PATH = "./src/main/resources/customer_blacklist.csv";
    private static final int UUID_IDX = 0;
    private static final int NAME_IDX = 1;
    private static final int EMAIL_IDX = 2;
    private static final int CREATED_IDX = 3;
    private static final int LAST_LOGIN_IDX = 4;


    public List<Customer> readBlack(){
        log.info("readBlack started");
        List<Customer> blacks = new ArrayList<>();

        File file = new File(BLACK_PATH);
        if (!file.exists()) {
            log.warn("file not exists.");
            throw new RuntimeException("The file does not exist.");
        }

        log.info("file exists.");
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(BLACK_PATH), StandardCharsets.UTF_8))){
            String line = br.readLine();

            while((line = br.readLine()) != null)
                addBlacks(line, blacks);
        }catch (IOException e){
            log.error("Unable to read the file due to an unexpected error.");
            throw new RuntimeException(e);
        }
        return blacks;
    }

    private void addBlacks(String line, List<Customer> blacks){
        log.debug("line = {}", line);

        List<String> splitLine = Arrays.stream(line.split(CSV_PATTERN))
                .map(s -> s.replaceAll("\"", "")).toList();

        UUID uuid = UUID.fromString(splitLine.get(UUID_IDX));
        String name = splitLine.get(NAME_IDX);
        String email = splitLine.get(EMAIL_IDX);
        LocalDateTime createdAt = LocalDateTime.parse(splitLine.get(CREATED_IDX));
        LocalDateTime lastLoginAt = LocalDateTime.parse(splitLine.get(LAST_LOGIN_IDX));

        blacks.add(
                new Customer(uuid, name, email, createdAt, lastLoginAt, false)
        );
    }

}
