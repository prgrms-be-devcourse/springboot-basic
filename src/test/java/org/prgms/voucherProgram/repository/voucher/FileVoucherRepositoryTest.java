package org.prgms.voucherProgram.repository.voucher;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucherProgram.domain.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.domain.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.exception.WrongFileException;

class FileVoucherRepositoryTest {

    VoucherRepository voucherRepository;

    @AfterEach
    void tearDown() {
        File file = new File("./voucherData.txt");
        file.delete();
    }

    @DisplayName("Voucher를 파일에 저장한다.")
    @Test
    void save_Voucher_File() {
        // given
        voucherRepository = new FileVoucherRepository("./voucherData.txt");
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        // when
        Voucher saveVoucher = voucherRepository.save(voucher);
        // then
        assertThat(saveVoucher).isEqualTo(voucher);
        assertThat(voucherRepository.findAll()).hasSize(1)
            .extracting("voucherId").contains(voucher.getVoucherId());

    }

    @DisplayName("파일에 저장되어 있는 모든 Voucher를 List형으로 반환한다.")
    @Test
    void findAll_ReturnAllVoucher() {
        // given
        voucherRepository = new FileVoucherRepository("./voucherData.txt");
        Voucher voucherOne = new FixedAmountVoucher(UUID.randomUUID(), 20L);
        Voucher voucherTwo = new PercentDiscountVoucher(UUID.randomUUID(), 20L);
        // when
        voucherRepository.save(voucherOne);
        voucherRepository.save(voucherTwo);
        // then
        assertThat(voucherRepository.findAll()).hasSize(2)
            .extracting("voucherId").contains(voucherOne.getVoucherId(), voucherTwo.getVoucherId());
    }

    @DisplayName("Voucher를 저장할 시 잘못된 파일일 경우 예외를 발생한다.")
    @Test
    void saveVoucher_WrongFilePath_ThrowException() {
        voucherRepository = new FileVoucherRepository("./");
        assertThatThrownBy(() -> voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L)))
            .isInstanceOf(WrongFileException.class)
            .hasMessage("[ERROR] 올바른 파일이 아닙니다.");
    }

    @DisplayName("Voucher를 찾을 시 파일이 아직 생성되지 않았을 경우 빈 List를 반환한다.")
    @Test
    void findAllVoucher_WrongFilePath_ThrowException() {
        //given
        voucherRepository = new FileVoucherRepository("./voucherData.txt");

        //when
        List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers).isEmpty();
    }
}
