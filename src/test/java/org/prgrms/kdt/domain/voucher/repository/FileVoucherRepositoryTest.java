package org.prgrms.kdt.domain.voucher.repository;

import org.junit.jupiter.api.*;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileVoucherRepositoryTest {

    FileVoucherRepository voucherRepository = new FileVoucherRepository();
    String csvPath = "src\\test\\resources";
    String fileName = "voucherList_sample.csv";

    /**
     * 리플렉션을 통해 csvPath, fileName을 설정해준다.
     */
    @BeforeEach
    public void setCsvFile() {
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
        LocalDateTime now = LocalDateTime.now();
        Voucher fixedVoucher = new Voucher(fixedVoucherId, VoucherType.FIXED_AMOUNT, 10000L, now, now);
        Voucher percentVoucher = new Voucher(percentVoucherId, VoucherType.PERCENT_DISCOUNT, 30L, now, now);
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
        LocalDateTime now = LocalDateTime.now();
        Voucher fixedVoucher = new Voucher(fixedVoucherId, VoucherType.FIXED_AMOUNT, 10000L, now, now);
        Voucher percentVoucher = new Voucher(percentVoucherId, VoucherType.PERCENT_DISCOUNT, 50L, now, now);
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
        LocalDateTime now = LocalDateTime.now();
        Voucher voucher = new Voucher(voucherId, VoucherType.FIXED_AMOUNT, 10000L, now, now);
        //when
        UUID savedVoucherId = voucherRepository.save(voucher);
        //then
        assertThat(savedVoucherId).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("바우처 전체조회 시 저장된 파일을 읽지 못할 경우 예외가 발생한다.")
    public void exception_findAll(){
        //given
        ReflectionTestUtils.setField(voucherRepository, "csvPath", "");
        //when
        //then
        assertThatThrownBy(() -> voucherRepository.findAll())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일의 경로 혹은 이름을 확인해주세요.");
    }

    @Test
    @DisplayName("바우처 ID로 조회 시 저장된 파일을 읽지 못할 경우 예외가 발생한다.")
    public void exception_findById(){
        //given
        ReflectionTestUtils.setField(voucherRepository, "csvPath", "");
        //when
        //then
        assertThatThrownBy(() -> voucherRepository.findById(UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일의 경로 혹은 이름을 확인해주세요.");
    }

    @Test
    @DisplayName("바우처 저장 시 파일을 읽지 못할 경우 예외가 발생한다.")
    public void exception_saveVoucher(){
        //given
        UUID fixedVoucherId = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        Voucher fixedVoucher = new Voucher(fixedVoucherId, VoucherType.FIXED_AMOUNT, 10000L, now, now);
        ReflectionTestUtils.setField(voucherRepository, "csvPath", "");
        //when
        //then
        assertThatThrownBy(() -> voucherRepository.save(fixedVoucher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("파일의 경로 혹은 이름을 확인해주세요.");
    }
}