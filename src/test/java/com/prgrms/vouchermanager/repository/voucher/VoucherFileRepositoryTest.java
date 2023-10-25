package com.prgrms.vouchermanager.repository.voucher;

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

    @BeforeEach
    void setup() {
        repository = new VoucherFileRepository(filePath);
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
    @DisplayName("바우처 생성")
    void createTest() {
        PercentAmountVoucher voucher = new PercentAmountVoucher(20);
        Voucher createVoucher = repository.create(voucher);

        Assertions.assertThat(createVoucher).isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처 목록")
    void listTest() {
        List<Voucher> list = repository.list();
        Assertions.assertThat(list.size()).isEqualTo(3);
    }
}
