package com.dev.voucherproject.blacklist;

import com.dev.voucherproject.model.customer.Customer;
import com.dev.voucherproject.model.storage.blacklist.CsvFileBlacklistStorage;
import com.dev.voucherproject.model.voucher.Voucher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("csv")
public class CsvFileBlacklistStorageTest {

    @Autowired
    CsvFileBlacklistStorage csvFileBlacklistStorage;

    @Test
    @DisplayName("customer_blacklist.csv 파일을 읽을 수 있다.")
    void findAll() {
        //WHEN
        List<Customer> customers = csvFileBlacklistStorage.findAll();

        //THEN
        assertThat(customers).hasSize(3);
    }
}
