package org.prgms.w3d1.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherService;
import org.prgms.w3d1.util.FileConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
class FileVoucherRepositoyTest {

    @Configuration
    static class config {
        @Bean
        VoucherRepository voucherRepository(){
            return new FileVoucherRepositoy();
        }

        @Bean
        VoucherService voucherService(VoucherRepository voucherRepository){
            return new VoucherService(voucherRepository);
        }
    }

    @Autowired
    VoucherService voucherService;

    @Autowired
    VoucherRepository voucherRepository;

    /*
        테스트 : 파일에서 불러온 storage의 voucher를 조회할 수있는가
        Given : storage에 Voucher 저장
        when : findById로 저장된 voucher 조회
        then : 조회된 voucher 비교
     */
    @Test
    void findById() {
        // Given
        Voucher voucher = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        voucherRepository.save(voucher);
        // When
        var testVoucher = voucherRepository.findById(voucher.getVoucherId());
        // then
        assertThat(testVoucher.get(), samePropertyValuesAs(voucher));

    }


    /*
        테스트 : 파일에 voucher를 저장할 수 있는가
        Given : 바우처 생성, 기존의 storage 용량 확인
        when : storage에 Voucher 저장
        then : 용량이 1 더해졌는지 확인
     */
    @Test
    void save() {
        // Given
        var voucher = FixedAmountVoucher.of(UUID.randomUUID(), 300L);
        var size = voucherRepository.findAll().size();

        // When
        voucherRepository.save(voucher);

        // then
        var testSize = voucherRepository.findAll().size();
        assertThat(testSize, is(size + 1));
    }


    /*
        테스트 : 파일에 있는 모든 Voucher를 얻을 수 있는가
        Given : 다수의 바우처 생성, storage에 다수의 Voucher 저장
        when : findAll 메서드를 통해서 리스트 생성
        then : list에 만든 voucher가 있는지 확인
     */
    @Test
    void findAll() {
        // Given
        Voucher voucher1 = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        Voucher voucher2 = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        Voucher voucher3 = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);

        // When
        var voucherList = voucherRepository.findAll();

        // then
        assertThat(voucherList, hasItems(voucher1, voucher2, voucher3));
    }
}