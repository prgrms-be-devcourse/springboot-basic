package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.repository.CustomerFileRepository;
import com.prgrms.vouchermanager.repository.VoucherFileRepository;
import com.prgrms.vouchermanager.service.VoucherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
class VoucherControllerTest {

    private final String filePathV = "src/main/resources/voucher_list.csv";
    private final String filePathC = "src/main/resources/customer_blacklist.csv";
    VoucherFileRepository voucherRepository = new VoucherFileRepository(filePathV);
    CustomerFileRepository customerRepository = new CustomerFileRepository(filePathC);
    VoucherService service = new VoucherService(voucherRepository, customerRepository);
    private final VoucherController controller = new VoucherController(service);

    @Test
    @DisplayName("list")
    void listTest() {
        List<Voucher> list = controller.list();
        Assertions.assertThat(list.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("blacklist")
    void blackList() {
        List<Customer> blacklist = controller.blacklist();
        Assertions.assertThat(blacklist.size()).isEqualTo(3);
    }

}
