package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.repository.voucher.VoucherFileRepository;
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
