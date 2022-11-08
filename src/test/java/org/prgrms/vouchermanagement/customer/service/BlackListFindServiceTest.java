package org.prgrms.vouchermanagement.customer.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.BlackListCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
class BlackListFindServiceTest {

    @Autowired
    private BlackListFindService blackListFindService;

    @Autowired
    private BlackListCustomerRepository blackListCustomerRepository;

    @Test
    @DisplayName("블랙리스트 정상 출력 확인")
    void findBlackList() throws IOException {
        // given
        String path = "src/main/resources/customer_blacklist.csv";
        Path blackListFile = Paths.get(path);
        List<String> originalBlackList = Files.readAllLines(blackListFile);

        // when
        List<Customer> findBlacklist = blackListFindService.findAllBlackList();

        // then
        Assertions.assertThat(originalBlackList.size()).isEqualTo(findBlacklist.size());
        for (int i = 0; i < findBlacklist.size(); i++) {
            String findCustomerId = findBlacklist.get(i).getCustomerId().toString();
            String findName = findBlacklist.get(i).getName();

            String originalCustomerId = originalBlackList.get(i).split(",")[0];
            String originalName = originalBlackList.get(i).split(",")[1];

            Assertions.assertThat(findCustomerId).isEqualTo(originalCustomerId);
            Assertions.assertThat(findName).isEqualTo(originalName);
        }
    }
}