package com.mountain.voucherApp.repository;

import com.mountain.voucherApp.properties.FileRepositoryProperties;
import com.mountain.voucherApp.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.voucher.Voucher;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

class FileVoucherRepositoryTest {

    // TODO properties 파일 읽어서 테스트하는 방법
    FileRepositoryProperties fileRepositoryProperties = new FileRepositoryProperties();
    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(fileRepositoryProperties);

    @Description("fileRepository insert 테스트")
    @Test
    public void fileInsertTest() throws Exception {
        for (int i = 0; i < 5; i++) {
            Voucher voucher = new PercentDiscountVoucher(10);
            // fileVoucherRepository.insert(voucher);
        }
    }

}