package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
import com.programmers.springweekly.util.validator.ParseValidator;
import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class FileCustomerRepository {

    private final List<Customer> customerList = new ArrayList<>();

    @Value("${file.customer.path}")
    private String filePath;

    public List<Customer> getBlackList() {
        return Collections.unmodifiableList(customerList);
    }

    private void saveIfBlacklistedCustomer(UUID uuid, String name, String email, String customerType) {
        CustomerType type = CustomerType.from(customerType);

        if (CustomerType.isBlacklistedCustomer(type)) {

            Customer customer = Customer.builder()
                    .customerId(uuid)
                    .customerName(name)
                    .customerEmail(email)
                    .customerType(CustomerType.BLACKLIST)
                    .build();

            customerList.add(customer);
        }
    }

    @PostConstruct
    public void loadingBlackListToMemory() {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath))) {
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                String[] readLine = line.split(",");
                ParseValidator.validateBlacklistFileLineLength(readLine);

                saveIfBlacklistedCustomer(UUID.fromString(readLine[0]), readLine[1], readLine[2], readLine[3]);
            }

        } catch (IllegalArgumentException e) {
            log.warn("바우처 파일에 있는 값을 읽어오던 중 바우처 필드와 타입이 다르거나 형식이 맞지 않아 예외 발생", e);
            throw new IllegalArgumentException("바우처 파일에 있는 값을 읽어오던 중 바우처 필드와 타입이 다르거나 형식이 맞지 않아 예외 발생, 파일을 다시 한 번 확인해보세요");
        } catch (IOException e) {
            log.error("블랙리스트 파일에 저장된 블랙리스트를 불러오던 중 알 수 없는 이유로 발생된 에러", e);
            throw new RuntimeException("파일에 저장된 블랙리스트를 불러오던 중 알 수 없는 에러가 발생되었습니다.");
        }
    }

}
