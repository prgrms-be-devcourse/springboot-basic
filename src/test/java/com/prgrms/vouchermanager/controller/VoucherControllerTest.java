package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.repository.voucher.VoucherFileRepository;
import com.prgrms.vouchermanager.service.VoucherService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig
class VoucherControllerTest {

    private final String filePathV = "src/main/resources/voucher_list.csv";
//    private final String filePathC = "src/main/resources/customer_blacklist.csv";
    VoucherFileRepository voucherRepository = new VoucherFileRepository(filePathV);
    VoucherService service = new VoucherService(voucherRepository);
    private final VoucherController controller = new VoucherController(service);

    @Test
    @DisplayName("list")
    void listTest() {
        List<Voucher> list = controller.list();
        Assertions.assertThat(list.size()).isEqualTo(3);
    }

//    @Test
//    @DisplayName("blacklist")
//    void blackList() {
//        List<Customer> blacklist = controller.blacklist();
//        Assertions.assertThat(blacklist.size()).isEqualTo(3);
//    }

}
