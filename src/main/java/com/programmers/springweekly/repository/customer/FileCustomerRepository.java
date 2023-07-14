package com.programmers.springweekly.repository.customer;

import com.programmers.springweekly.domain.customer.Customer;
import com.programmers.springweekly.domain.customer.CustomerType;
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

    private void saveIfBlacklistedCustomer(String uuid, String name, String email, String customerType) {
        CustomerType type = CustomerType.from(customerType);

        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            log.warn("사용자가 입력한 {} 문자열이 UUID 형식에 알맞지 않는 예외 발생 ", uuid, e);
            throw new IllegalArgumentException("입력하신 " + uuid + " 문자열은 UUID 형식에 알맞지 않습니다. 다시 입력 해주세요");
        }

        if (CustomerType.isBlacklistedCustomer(type)) {

            Customer customer = Customer.builder()
                    .customerId(UUID.fromString(uuid))
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
                saveIfBlacklistedCustomer(readLine[0], readLine[1], readLine[2], readLine[3]);
            }

        } catch (IndexOutOfBoundsException e) {
            log.warn("블랙리스트 파일에 저장된 열의 수가 맞지 않아 발생된 예외 ", e);
            throw new IllegalArgumentException("현재 파일에 저장된 열의 수가 맞지않아 발생된 예외입니다. 파일을 다시 한 번 확인해주세요");
        } catch (IOException e) {
            log.error("블랙리스트 파일에 저장된 블랙리스트를 불러오던 중 알 수 없는 이유로 발생된 에러", e);
            throw new RuntimeException("파일에 저장된 블랙리스트를 불러오던 중 알 수 없는 에러가 발생되었습니다.");
        }
    }

}
