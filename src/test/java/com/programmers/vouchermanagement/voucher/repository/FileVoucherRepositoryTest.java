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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.programmers.vouchermanagement.configuration.properties.file.FileProperties;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    void testLoadingVoucherFileOnInit() {
        assertThat(fileProperties.getVoucherFilePath(), is("src/test/resources/voucher-test.json"));
        assertThat(voucherRepository, notNullValue());
    }

    @Test
    @DisplayName("저장된 바우처가 없을 때 빈 리스트를 반환한다.")
    void testListVoucherSuccessful_ReturnEmptyList() {
        //given
        voucherRepository.deleteAll();

        //when
        List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처 저장에 성공한다.")
    void testVoucherCreationSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);

        //when
        Voucher createdVoucher = voucherRepository.save(voucher);

        //then
        assertThat(createdVoucher, samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("추가적으로 바우처를 저장하고 저장된 바우처들의 리스트를 반환한다.")
    void testListVoucherSuccessful_ReturnList() {
        //given
        Voucher additionalVoucher = new Voucher(UUID.randomUUID(), new BigDecimal(50), VoucherType.PERCENT);
        voucherRepository.save(additionalVoucher);

        //when
        List<Voucher> vouchers = voucherRepository.findAll();

        //then
        assertThat(vouchers, hasSize(greaterThanOrEqualTo(1)));
    }

    @Test
    @DisplayName("존재하지 않는 아이디 검색 시 빈 Optional을 반환한다")
    void testFindVoucherByIdSuccessful_ReturnEmptyOptional() {
        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(UUID.randomUUID());

        //then
        assertThat(foundVoucher.isEmpty(), is(true));
    }

    @Test
    @DisplayName("아이디 검색으로 바우처 조회를 성공한다.")
    void testFindVoucherByIdSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(40), VoucherType.PERCENT);
        voucherRepository.save(voucher);

        //when
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        //then
        assertThat(foundVoucher.isEmpty(), is(false));
        assertThat(foundVoucher.get(), samePropertyValuesAs(voucher));
    }

    @Test
    @DisplayName("바우처 정보 수정을 성공한다.")
    void testVoucherUpdateSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(10000), VoucherType.FIXED);
        voucherRepository.save(voucher);
        Voucher foundVoucher = voucherRepository.findById(voucher.getVoucherId()).get();

        //when
        Voucher updatedVoucher = new Voucher(voucher.getVoucherId(), new BigDecimal(5000), voucher.getVoucherType());
        voucherRepository.save(updatedVoucher);
        Voucher newlyFoundVoucher = voucherRepository.findById(foundVoucher.getVoucherId()).get();

        //then
        assertThat(newlyFoundVoucher, samePropertyValuesAs(updatedVoucher));
    }

    @Test
    @DisplayName("바우처 삭제를 성공한다")
    void testDeleteVoucherSuccessful() {
        //given
        Voucher voucher = new Voucher(UUID.randomUUID(), new BigDecimal(50), VoucherType.PERCENT);
        voucherRepository.save(voucher);

        //when
        voucherRepository.deleteById(voucher.getVoucherId());

        //then
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucher.getVoucherId());
        assertThat(foundVoucher.isEmpty(), is(true));
    }
}
