package org.prgrms.kdt.voucher.dao;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.prgrms.kdt.exception.FileAccessException;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FileVoucherRepositoryTest {
    static VoucherLoader mockVoucherLoader;

    @Configuration
    static class TestConfig {

        @Bean
        FileVoucherRepository fileVoucherRepository() {
            mockVoucherLoader = Mockito.mock(VoucherLoader.class);
            return new FileVoucherRepository(mockVoucherLoader);
        }
    }

    FileVoucherRepository fileVoucherRepository;
    AnnotationConfigApplicationContext testContext;

    @BeforeEach
    void setup() {
        testContext = new AnnotationConfigApplicationContext(TestConfig.class);
        fileVoucherRepository = testContext.getBean(FileVoucherRepository.class);
        BDDMockito.given(mockVoucherLoader.loadFileToMemoryVoucher()).willReturn(new ConcurrentHashMap<>());
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("존재하는 바우처Id로 바우처 찾기")
    void findByExistId(List<Voucher> voucherList) {
        //given
        voucherList.forEach(v -> fileVoucherRepository.insert(v));
        UUID existVoucherId = voucherList.get(0).getVoucherId();

        //when
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(existVoucherId);

        //then
        UUID foundVoucherId = foundVoucher.get().getVoucherId();
        assertThat(foundVoucherId, Matchers.is(existVoucherId));
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("존재하지 않는 바우처Id로 바우처 찾기")
    void findByNonExistId(List<Voucher> voucherList) {
        //given
        voucherList.forEach(v -> fileVoucherRepository.insert(v));
        UUID notExistVoucherId = UUID.randomUUID();

        //when
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(notExistVoucherId);

        //then
        assertThrows(FileAccessException.class, () -> {
            foundVoucher.orElseThrow(FileAccessException::new);
        });
    }

    @Test
    @DisplayName("바우처 저장 후 성공적으로 저장 되었는지 확인")
    void insert() {
        //given
        Voucher insertVoucher = new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0));

        //when
        fileVoucherRepository.insert(insertVoucher);

        //then
        Optional<Voucher> foundVoucher = fileVoucherRepository.findById(insertVoucher.getVoucherId());
        assertThat(foundVoucher.get(), is(insertVoucher));
    }

    @ParameterizedTest
    @MethodSource("voucherSource")
    @DisplayName("바우처 전체 조회 테스트")
    void findAll(List<Voucher> voucherList) {
        //given
        voucherList.forEach(v -> fileVoucherRepository.insert(v));

        //when
        List<Voucher> foundVoucherList = fileVoucherRepository.findAll();

        //then
        Voucher insertedVoucher1 = voucherList.get(0);
        Voucher insertedvoucher2 = voucherList.get(1);
        assertThat(foundVoucherList, containsInAnyOrder(insertedVoucher1, insertedvoucher2));
    }

    @Test
    @DisplayName("FileVoucherRepository의 빈이 소멸할 때 fileWrite를 호출하는지 확인")
    void fileWrite() {
        //when
        testContext.close();

        //then
        verify(mockVoucherLoader, times(1)).saveMemoryVoucherToFile(anyMap());
    }

    static Stream<Arguments> voucherSource() {
        List<Voucher> voucherList = new ArrayList<>();
        voucherList.add(new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0)));
        voucherList.add(new Voucher(VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0)));
        return Stream.of(Arguments.of(voucherList));
    }
}