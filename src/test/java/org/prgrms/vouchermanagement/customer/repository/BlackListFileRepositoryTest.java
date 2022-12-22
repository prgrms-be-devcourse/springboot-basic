package org.prgrms.vouchermanagement.customer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanagement.customer.domain.Customer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static java.awt.Color.black;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class BlackListFileRepositoryTest {

    private final String path = "src/test/resources/test_customer_blacklist.csv";
    private final BlackListFileRepository blackListFileRepository = new BlackListFileRepository(path);

    @BeforeEach
    void init() {
        try {
            Files.write(Paths.get(path), "".getBytes());
        } catch (IOException e) {
        }
    }

    @Test
    @DisplayName("블랙리스트 모두 조회 성공")
    void findAllSuccess() {

        // given
        List<Customer> blackCustomers = List.of(
                makeBlackCustomer("Kim", "Kim@google.com"),
                makeBlackCustomer("Park", "Park@google.com"),
                makeBlackCustomer("Joe", "Joe@google.com"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            for (Customer blackCustomer : blackCustomers) {
                writer.write(blackCustomer.getCustomerId() + "," + blackCustomer.getName() + "," + blackCustomer.getEmail() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        };


        // when
        List<Customer> blackList = blackListFileRepository.findAll();

        // then
        assertThat(blackList).hasSize(3);
        assertThat(blackList)
                .extracting("customerId", "name")
                .contains(tuple(blackCustomers.get(0).getCustomerId(), blackCustomers.get(0).getName()),
                        tuple(blackCustomers.get(1).getCustomerId(), blackCustomers.get(1).getName()),
                        tuple(blackCustomers.get(2).getCustomerId(), blackCustomers.get(2).getName()));
    }

    private Customer makeBlackCustomer(String name, String email) {
        return Customer.createBlackCustomer(UUID.randomUUID(), name, email);
    }
}