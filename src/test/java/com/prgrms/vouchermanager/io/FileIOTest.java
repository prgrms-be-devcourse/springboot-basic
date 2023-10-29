package com.prgrms.vouchermanager.io;

import com.prgrms.vouchermanager.domain.customer.Customer;
import com.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class FileIOTest {

    private final Map<UUID, Voucher> voucherMap = new HashMap<>();
    private final Map<UUID, Customer> customerMap = new HashMap<>();
    private final String filePathV = "src/main/resources/voucher_list.csv";
    private final FileIO fileIoV = new FileIO(filePathV);
    private final String filePathC = "src/main/resources/customer_blacklist.csv";
    private final FileIO fileIoC = new FileIO(filePathC);

    @BeforeEach
    void beforeEach() {
        FixedAmountVoucher voucher1 = new FixedAmountVoucher(20000);
        PercentAmountVoucher voucher2 = new PercentAmountVoucher(20);
        FixedAmountVoucher voucher3 = new FixedAmountVoucher(40000);

        voucherMap.put(voucher1.getId(), voucher1);
        voucherMap.put(voucher2.getId(), voucher2);
        voucherMap.put(voucher3.getId(), voucher3);
    }

    @AfterEach
    void afterEach() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePathV));

        bw.write("""
                70754a2f-d87d-4f69-af71-1d4bfe855e28,fixed,300
                626b8d5d-3940-4a0d-a3e4-fe6b297e8ad0,percent,20
                8213dfa7-d577-4bb5-86d6-0159b3383f0e,fixed,20000""");
        bw.close();
    }

    @Test
    @DisplayName("voucher updateFile")
    void updateFile() throws IOException {
        fileIoV.updateFile(voucherMap);

        BufferedReader bf = new BufferedReader(new FileReader(filePathV));

        int i = 0;
        while(bf.readLine() != null) i++;
        Assertions.assertThat(i).isEqualTo(3);
    }

    @Test
    @DisplayName("file to customerMap")
    void fileToCustomerMap() {
        fileIoC.readCustomerFile(customerMap);

        Assertions.assertThat(customerMap.size()).isEqualTo(3);
    }

    @Test
    void fileToVoucherMap() {
        voucherMap.clear();
        fileIoV.fileToVoucherMap(voucherMap);

        Assertions.assertThat(voucherMap.size()).isEqualTo(3);
    }
}
