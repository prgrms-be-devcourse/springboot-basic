package org.prgms.voucheradmin.domain.voucher.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.global.properties.VoucherAdminProperties;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class FileVoucherRepositoryTest {
    @InjectMocks
    FileVoucherRepository fileVoucherRepository;

    @Mock
    VoucherAdminProperties voucherAdminProperties;

    @Test
    @DisplayName("파일 저장 조회 테스트")
    void testSaveAndGetAll() {
        UUID voucherId = UUID.randomUUID();
        Voucher newVoucher = new PercentageDiscountVoucher(voucherId, 10L);
        when(voucherAdminProperties.getVoucherFilePath()).thenReturn("src/main/java/org/prgms/voucheradmin/global/filedata/voucher.txt");

        try {
            fileVoucherRepository.create(newVoucher);

            List<Voucher> vouchers = fileVoucherRepository.getAll().stream()
                    .filter(voucher -> voucher.getVoucherId().equals(voucherId))
                    .collect(Collectors.toList());

            assertThat(vouchers.size(), is(1));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}