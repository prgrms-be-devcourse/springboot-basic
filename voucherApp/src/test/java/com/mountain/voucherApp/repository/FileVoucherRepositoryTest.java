package com.mountain.voucherApp.repository;

import com.mountain.voucherApp.properties.FileProperties;
import com.mountain.voucherApp.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.voucher.Voucher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;

@Profile("local")
class FileVoucherRepositoryTest {

    // TODO properties 파일 읽어서 테스트하는 방법
    FileProperties fileProperties = new FileProperties();
    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(fileProperties);

    @Description("fileRepository insert 테스트")
    @Test
    public void fileInsertTest() throws Exception {
        for (int i = 0; i < 5; i++) {
            Voucher voucher = new PercentDiscountVoucher(10);
            // fileVoucherRepository.insert(voucher);
        }
    }

}