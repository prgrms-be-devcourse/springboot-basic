package org.prgrms.kdt.domain.voucher.repository;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FileVoucherRepositoryTest {

    private static final VoucherRepository voucherRepository = new FileVoucherRepository();
    private static final String csvPath = "src\\test\\resources";
    private static final String fileName = "voucherList_sample.csv";

    /**
     * 리플렉션을 통해 csvPath, fileName을 설정해준다.
     */
    @BeforeAll
    public static void setCsvFile() {
        ReflectionTestUtils.setField(voucherRepository, "csvPath", csvPath);
        ReflectionTestUtils.setField(voucherRepository, "fileName", fileName);
    }

    /**
     * 각 테스트 실행 후 csv에 있는 내용 전부를 지운다.
     */
    @AfterEach
    void deleteCsvContents() throws IOException {
        new FileOutputStream(csvPath+"\\"+fileName).close();
    }

    @Test
    @DisplayName("csv에 저장된 바우처들을 조회할 수 있다.")
    public void findAllVouchers(){
        //given
        UUID fixedVoucherId = UUID.randomUUID();
        UUID percentVoucherId = UUID.randomUUID();
        Voucher fixedVoucher = new FixedAmountVoucher(fixedVoucherId, 10000);
        Voucher percentVoucher = new PercentDiscountVoucher(percentVoucherId, 30);
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(percentVoucher);
        //when
        List<Voucher> vouchers = voucherRepository.findAll();
        //then
        assertThat(vouchers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("csv를 읽어, 저장된 바우처 중 특정 ID값을 가진 바우처를 조회할 수 있다.")
    public void findById(){
        //given
        UUID fixedVoucherId = UUID.randomUUID();
        UUID percentVoucherId = UUID.randomUUID();
        Voucher fixedVoucher = new FixedAmountVoucher(fixedVoucherId, 10000);
        Voucher percentVoucher = new PercentDiscountVoucher(percentVoucherId, 50);
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(percentVoucher);
        //when
        Optional<Voucher> findFixedVoucher = voucherRepository.findById(fixedVoucherId);
        Optional<Voucher> findPercentVoucher = voucherRepository.findById(percentVoucherId);
        //then
        assertThat(findFixedVoucher.orElse(null).getVoucherId())
                .isEqualTo(fixedVoucher.getVoucherId());
        assertThat(findPercentVoucher.orElse(null).getVoucherId())
                .isEqualTo(percentVoucher.getVoucherId());
    }

    @Test
    @DisplayName("csv에 바우처를 정상적으로 저장한다.")
    public void saveVoucher(){
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId, 10000);
        //when
        UUID savedVoucherId = voucherRepository.save(voucher);
        //then
        assertThat(savedVoucherId).isEqualTo(voucherId);
    }
}