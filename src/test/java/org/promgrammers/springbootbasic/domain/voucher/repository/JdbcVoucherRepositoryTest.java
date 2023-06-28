package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.promgrammers.springbootbasic.controller.CommandLineController;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yaml")
@ActiveProfiles("jdbc")
class JdbcVoucherRepositoryTest {

    @MockBean
    private CommandLineController controller;

    @Autowired
    JdbcVoucherRepository jdbcTemplateVoucherRepository;

    @BeforeEach
    void init() {
        jdbcTemplateVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("저장 성공 - 바우처 저장")
    void insert() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);

        //when
        Voucher savedVoucher = assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.insert(voucher));

        //then
        assertNotNull(savedVoucher);
        assertThat(savedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(savedVoucher.getVoucherType()).isEqualTo(voucher.getVoucherType());
        assertThat(savedVoucher.getAmount()).isEqualTo(voucher.getAmount());
    }

    @Test
    @DisplayName("저장 실패 - 중복 바우처 저장")
    void insertDuplicateVoucherTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);

        //when
        assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.insert(voucher));

        //then
        assertThrows(DataAccessException.class, () -> jdbcTemplateVoucherRepository.insert(voucher));
    }

    @Test
    @DisplayName("단건 조회 성공 - 바우처 조회")
    void findByIdSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        Voucher savedVoucher = assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.insert(voucher));

        //when
        Optional<Voucher> repositoryById = assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.findById(savedVoucher.getVoucherId()));
        Voucher foundVoucher = repositoryById.get();

        //then
        assertThat(repositoryById.isPresent()).isEqualTo(true);
        assertThat(foundVoucher.getVoucherId()).isEqualTo(savedVoucher.getVoucherId());
        assertThat(foundVoucher.getVoucherType()).isEqualTo(savedVoucher.getVoucherType());
        assertThat(foundVoucher.getAmount()).isEqualTo(savedVoucher.getAmount());
    }

    @Test
    @DisplayName("예외 테스트 - 존재하지 않는 바우처 조회")
    void findByIdNonexistentVoucherTest() throws Exception {

        // given
        UUID nonExistentVoucherId = UUID.randomUUID();

        // when
        Optional<Voucher> repositoryById = assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.findById(nonExistentVoucherId));

        // then
        assertThat(repositoryById.isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("전체 조회 성공 - 바우처 전체 조회")
    void findAllSuccessTest() throws Exception {

        //given
        int saveCount = 5;
        for (int i = 1; i <= saveCount; i++) {
            Voucher voucher;
            if (i % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), i);
                jdbcTemplateVoucherRepository.insert(voucher);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), i);
                jdbcTemplateVoucherRepository.insert(voucher);
            }
        }

        //when
        List<Voucher> voucherList = assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.findAll());

        //then
        assertThat(voucherList.isEmpty()).isEqualTo(false);
        assertThat(voucherList.size()).isEqualTo(saveCount);
    }

    @Test
    @DisplayName("업데이트 성공 - 바우처 업데이트")
    void updateSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 10;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        Voucher savedVoucher = assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.insert(voucher));

        //when
        long updateAmount = 20;
        FixedAmountVoucher updateVoucher = new FixedAmountVoucher(savedVoucher.getVoucherId(), updateAmount);
        assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.update(updateVoucher));
        Optional<Voucher> updatedVoucherById = assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.findById(updateVoucher.getVoucherId()));

        //then
        assertThat(updatedVoucherById.isPresent()).isEqualTo(true);
        assertThat(updatedVoucherById.get().getAmount()).isEqualTo(updateAmount);
        assertThat(updatedVoucherById.get().getVoucherId()).isEqualTo(savedVoucher.getVoucherId());
    }

    @Test
    @DisplayName("전체 삭제 - 바우처 전체 삭제")
    void deleteAllSuccessTest() throws Exception {

        //given
        int saveCount = 5;
        for (int i = 1; i <= saveCount; i++) {
            Voucher voucher;
            if (i % 2 == 0) {
                voucher = new PercentDiscountVoucher(UUID.randomUUID(), i);
                jdbcTemplateVoucherRepository.insert(voucher);
            } else {
                voucher = new FixedAmountVoucher(UUID.randomUUID(), i);
                jdbcTemplateVoucherRepository.insert(voucher);
            }
        }

        //when
        assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.deleteAll());
        List<Voucher> voucherList = assertDoesNotThrow(() -> jdbcTemplateVoucherRepository.findAll());

        //then
        assertThat(voucherList.size()).isEqualTo(0);
        assertThat(voucherList.isEmpty()).isEqualTo(true);
    }
}