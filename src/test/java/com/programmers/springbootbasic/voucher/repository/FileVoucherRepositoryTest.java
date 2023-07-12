package com.programmers.springbootbasic.voucher.repository;

import com.programmers.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.programmers.springbootbasic.voucher.domain.PercentDiscountVoucher;
import com.programmers.springbootbasic.voucher.domain.Voucher;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileVoucherRepositoryTest {

    String filePath = "src/test/resources/voucher_file.csv";
    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository(filePath);

    List<Voucher> vouchers;

    @BeforeAll
    void before() {
        vouchers = fileVoucherRepository.findAll();
    }

    @AfterAll
    void after() {
        vouchers.stream().forEach(fileVoucherRepository::save);
    }

    @DisplayName("파일에 기록된 바우처를 조회한다")
    @Test
    void findAll() {
        //given
        Voucher expected = new FixedAmountVoucher(UUID.fromString("e876b46c-c5d9-4ee7-83b6-779aa2416a47"), "Fixed1", 2L);

        //when
        List<Voucher> result = fileVoucherRepository.findAll();

        //then
        assertThat(result.get(0).getVoucherId(), is(expected.getVoucherId()));
    }


    @DisplayName("바우처를 id로 조회한다")
    @Test
    void findById() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);
        fileVoucherRepository.save(fixedAmountVoucher);

        //when
        Optional<Voucher> result = fileVoucherRepository.findById(fixedAmountVoucher.getVoucherId());

        //then
        assertThat(result.get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
    }

    @DisplayName("바우처를 id로 조회했을 때 존재하지 않으면 예외처리한다")
    @Test
    void findByIdException() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);

        //when
        //then
        assertThatThrownBy(() -> fileVoucherRepository.findById(fixedAmountVoucher.getVoucherId()))
                .isInstanceOf(RuntimeException.class);
    }

    @DisplayName("바우처를 수정한다")
    @Test
    void update() {
        //given
        UUID id = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(id, "testName", 10L);
        fileVoucherRepository.save(fixedAmountVoucher);

        //when
        Voucher result = fileVoucherRepository.update(new FixedAmountVoucher(id, "update", 20L));

        //then
        assertThat(result.getVoucherId(), is(id));
        assertThat(result.getVoucherName(), is("update"));
        assertThat(result.getVoucherValue(), is(20L));
    }

    @DisplayName("id로 바우처를 삭제한다")
    @Test
    void deleteById() {
        //given
        int beforeSize = fileVoucherRepository.findAll().size();

        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName", 10L);
        fileVoucherRepository.save(fixedAmountVoucher);

        //when
        fileVoucherRepository.deleteById(fixedAmountVoucher.getVoucherId());
        List<Voucher> result = fileVoucherRepository.findAll();

        //then
        assertThat(result.size(), is(beforeSize));
    }

    @DisplayName("저장된 모든 바우처들을 삭제한다")
    @Test
    void deleteAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), "testName1", 10L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), "testName2", 20L);

        fileVoucherRepository.save(fixedAmountVoucher);
        fileVoucherRepository.save(percentDiscountVoucher);

        //when
        fileVoucherRepository.deleteAll();
        List<Voucher> result = fileVoucherRepository.findAll();

        //then
        org.assertj.core.api.Assertions.assertThat(result).isEmpty();
    }

    @DisplayName("바우처 정보를 추출한다")
    @Test
    void extractVoucher() {
        //given
        String line = "[ Voucher Type = Fixed Amount Voucher, Id = e876b46c-c5d9-4ee7-83b6-779aa2416a47, discount amount = 2, voucher name = Fixed1 ]";
        Voucher expected = new FixedAmountVoucher(UUID.fromString("e876b46c-c5d9-4ee7-83b6-779aa2416a47"), "Fixed1", 2L);

        //when
        Voucher result = fileVoucherRepository.extractVoucher(line);

        //then
        assertThat(result.getVoucherId(), is(expected.getVoucherId()));
    }
}