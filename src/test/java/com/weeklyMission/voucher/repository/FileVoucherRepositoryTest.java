package com.weeklyMission.voucher.repository;

import static org.assertj.core.api.Assertions.*;

import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileVoucherRepositoryTest {

    FileVoucherRepository fileVoucherRepository;

    @BeforeAll
    void init(){
        fileVoucherRepository = new FileVoucherRepository("/src/main/resources/csv/voucherRepository.csv");
        fileVoucherRepository.init();
    }

    @Test
    @Order(1)
    @DisplayName("저장 테스트")
    void save(){
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10);

        //when
        Voucher saveVoucher = fileVoucherRepository.save(voucher);

        //then
        assertThat(voucher).isEqualTo(saveVoucher);
    }

    @Test
    @Order(2)
    @DisplayName("파일 동기화 테스트")
    void write(){
        //given
        int basicSize = fileVoucherRepository.findAll().size();

        //when
        fileVoucherRepository.close();
        fileVoucherRepository.init();

        //then
        assertThat(basicSize).isEqualTo(fileVoucherRepository.findAll().size());
    }
}
