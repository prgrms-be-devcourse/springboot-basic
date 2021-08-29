package org.prgrms.orderapp.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.orderapp.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


class BlacklistCustomerRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(BlacklistCustomerRepositoryTest.class);

    private static final String PREFIX = "data";
    private static final String FILENAME = "customer_blacklist_test.csv";
    private static final int TEST_DATA_LEN = 3;

    BlacklistCustomerRepository customerRepository = new BlacklistCustomerRepository(PREFIX, FILENAME);

    @BeforeAll
    static void init() {
        String path = MessageFormat.format("{0}/{1}/{2}", System.getProperty("user.dir"), PREFIX, FILENAME);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            String testData = "fbb2ae1c-864f-41e1-a30a-d80beccb25ca,\"John Doe\",\"Seoul, South Korea\",25\n" +
                    "ad5c4166-29d5-42a5-b1d5-4255bb879bda,\"Kevin Park\",\"SanFrancisco, United States\",36\n" +
                    "d0fd414d-e124-44fe-8a38-9ef91a4c321b,\"Allen Song\",\"Tokyo, Japan\",50";
            writer.write(testData);
        } catch (IOException e) {
            logger.error("Error during writing test data");
        }
    }

    @Test
    @DisplayName("블랙리스트 고객 명단을 가져올 수 있다.")
    void testGetBlacklist() {
        List<Customer> blacklist = customerRepository.getBlacklist();
        assertThat(blacklist.size(), is(TEST_DATA_LEN));
    }

    @Test
    @DisplayName("블랙리스트 고객을 저장할 수 있다.")
    void testSave() {
        int prevSize = customerRepository.size();
        Customer customer = new Customer(UUID.randomUUID(), "test", "test address", 10);

        customerRepository.save(customer);

        assertThat(customerRepository.size(), is(prevSize + 1));
    }
}