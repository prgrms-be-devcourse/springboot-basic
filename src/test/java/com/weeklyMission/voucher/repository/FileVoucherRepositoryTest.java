package com.weeklyMission.voucher.repository;

import static org.assertj.core.api.Assertions.*;

import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.PercentDiscountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;


@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileVoucherRepositoryTest {

    FileVoucherRepository fileVoucherRepository;

    Voucher voucher;
    UUID voucherId;


    @BeforeAll
    void init(){
        fileVoucherRepository = new FileVoucherRepository("/src/test/resources/csv/voucherRepository.csv");
        fileVoucherRepository.init();

        voucherId = UUID.randomUUID();
        voucher = new FixedAmountVoucher(voucherId, 30);
    }

    @Test
    @DisplayName("저장 테스트")
    void save(){
        //when
        Voucher saveVoucher = fileVoucherRepository.save(voucher);

        //then
        assertThat(voucher).isEqualTo(saveVoucher);
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void findAll() {
        //when
        fileVoucherRepository.save(voucher);

        //then
        assertThat(fileVoucherRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("단일 조회 테스트")
    void findById() {
        //when
        Voucher saveVoucher = fileVoucherRepository.save(voucher);
        
        //then
        assertThat(fileVoucherRepository.findById(voucherId).get()).isEqualTo(saveVoucher);
    }

    @Test
    void deleteById() {
        //given
        Voucher saveVoucher = fileVoucherRepository.save(voucher);
        assertThat(saveVoucher).isEqualTo(voucher);

        //when
        fileVoucherRepository.deleteById(voucherId);

        //then
        assertThat(fileVoucherRepository.findById(voucherId)).isNotPresent();
    }
}
