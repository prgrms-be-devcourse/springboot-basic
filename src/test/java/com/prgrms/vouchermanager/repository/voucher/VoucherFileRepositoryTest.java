package com.prgrms.vouchermanager.repository.voucher;

import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class VoucherFileRepositoryTest {

    private VoucherFileRepository repository;
    private final String filePath = "src/main/resources/voucher_list.csv";
    private final Voucher voucher1 = new FixedAmountVoucher(20000);
    private final Voucher voucher2 = new PercentAmountVoucher(10);

    @BeforeEach
    void setup() {
        repository = new VoucherFileRepository(filePath);
        repository.create(voucher2);
    }

    @AfterEach
    void afterEach() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));

        bw.write("""
                70754a2f-d87d-4f69-af71-1d4bfe855e28,fixed,300
                626b8d5d-3940-4a0d-a3e4-fe6b297e8ad0,percent,20
                8213dfa7-d577-4bb5-86d6-0159b3383f0e,fixed,20000""");
        bw.close();
    }

    @Test
    @DisplayName("create")
    void create() {
        Voucher createVoucher = repository.create(voucher1);
        Assertions.assertThat(createVoucher).isSameAs(voucher1);
    }

    @Test
    @DisplayName("list")
    void list() {
        List<Voucher> list = repository.findAll();
        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("findById")
    void findById() {
        Voucher voucher = repository.create(voucher1);
        Voucher findVoucher = repository.findById(voucher.getId());

        Assertions.assertThat(findVoucher.getDiscount()).isEqualTo(voucher.getDiscount());
        Assertions.assertThat(findVoucher).isInstanceOf(FixedAmountVoucher.class);
    }

    @Test
    @DisplayName("updateDiscount")
    void updateDiscount() {
        Voucher voucher = new PercentAmountVoucher(voucher2.getId(), 20);
        Voucher updateVoucher = repository.updateDiscount(voucher);
        Assertions.assertThat(updateVoucher.getDiscount()).isEqualTo(20);
    }

    @Test
    @DisplayName("delete")
    void delete() {
        repository.delete(voucher2.getId());
        Assertions.assertThat(repository.findAll().size()).isEqualTo(3);
    }
}
