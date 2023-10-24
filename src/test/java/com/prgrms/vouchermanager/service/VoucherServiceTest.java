package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.io.VoucherType;
import com.prgrms.vouchermanager.repository.BlacklistFileRepository;
import com.prgrms.vouchermanager.repository.VoucherFileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class VoucherServiceTest {

    private final String filePathV = "src/main/resources/voucher_list.csv";
//    private final String filePathC = "src/main/resources/customer_blacklist.csv";
    private final VoucherService service = new VoucherService(new VoucherFileRepository(filePathV));

    @Test
    @DisplayName("create")
    void createTest() {
        Voucher voucher = service.create(VoucherType.FIXED, 20000);

        Assertions.assertThat(voucher.getDiscount()).isEqualTo(20000);
        Assertions.assertThat(voucher instanceof FixedAmountVoucher).isTrue();
    }
    @Test
    @DisplayName("list")
    void listTest() {
        List<Voucher> list = service.list();
        Assertions.assertThat(list.size()).isEqualTo(3);
    }

//    @Test
//    @DisplayName("blacklist")
//    void blackList() {
//        List<Customer> blacklist = service.blacklist();
//        Assertions.assertThat(blacklist.size()).isEqualTo(3);
//    }
}
