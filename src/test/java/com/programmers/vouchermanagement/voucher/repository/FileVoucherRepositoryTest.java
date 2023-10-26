package com.programmers.vouchermanagement.voucher.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.programmers.vouchermanagement.configuration.TestConfig;
import com.programmers.vouchermanagement.configuration.properties.FileProperties;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

@SpringJUnitConfig(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class FileVoucherRepositoryTest {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    FileProperties fileProperties;

    @BeforeAll
    void setUp() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("저장된 json파일을 성공적으로 읽고 로드한다.")
    @Order(1)
    void testLoadingVoucherFileOnInit() {
        assertThat(fileProperties.getVoucherFilePath(), is("src/test/resources/voucher-test.json"));
        assertThat(voucherRepository, notNullValue());
    }

    @Test
    @DisplayName("저장된 바우처가 없을 때 빈 리스트를 반환한다.")
    @Order(2)
    void testListVoucherSuccessful_ReturnEmptyList() {
        //when
        final List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 저장에 성공한다.")
    @Order(3)
    void testVoucherCreationSuccessful() {
        //given
        final Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);

        //when
        final Voucher createdVoucher = voucherRepository.save(voucher);

        //then
        assertThat(createdVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("추가적으로 바우처를 저장하고 저장된 바우처들의 리스트를 반환한다.")
    @Order(4)
    void testListVoucherSuccessful_ReturnList() {
        //given
        final Voucher additionalVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(50), VoucherType.PERCENT);
        voucherRepository.save(additionalVoucher);

        //when
        final List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers, hasSize(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("존재하지 않는 아이디 검색 시 빈 Optional을 반환한다")
    void testFindVoucherByIdSuccessful_ReturnEmptyOptional() {
        //when
        final Optional<Voucher> foundVoucher = voucherRepository.findById(UUID.randomUUID());

        //then
        assertThat(foundVoucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("아이디 검색으로 바우처 조회를 성공한다.")
    void testFindVoucherByIdSuccessful() {
        //given
        final Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT);
        voucherRepository.save(voucher);

        //when
        final Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        //then
        assertThat(foundVoucher.isEmpty(), is(false));
        assertThat(foundVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("바우처 정보 수정을 성공한다.")
    void testVoucherUpdateSuccessful() {
        //given
        final Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);
        final Voucher foundVoucher = voucherRepository.findById(voucher.getVoucherId()).get();

        //when
        final Voucher updatedVoucher = new Voucher(voucher.getVoucherId(), new BigDecimal(5000), voucher.getVoucherType());
        voucherRepository.save(updatedVoucher);
        final Voucher newlyFoundVoucher = voucherRepository.findById(foundVoucher.getVoucherId()).get();

        //then
        assertThat(newlyFoundVoucher, samePropertyValuesAs(updatedVoucher));
    }

    @Test
    @DisplayName("바우처 삭제를 성공한다")
    void testDeleteVoucherSuccessful() {
        //given
        final Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(50), VoucherType.PERCENT);
        voucherRepository.save(voucher);

        //when
        voucherRepository.deleteById(voucher.getVoucherId());

        //then
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(foundVoucher.isEmpty(), is(true));
    }
}
