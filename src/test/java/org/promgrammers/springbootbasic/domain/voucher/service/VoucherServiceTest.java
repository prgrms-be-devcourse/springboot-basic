package org.promgrammers.springbootbasic.domain.voucher.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.promgrammers.springbootbasic.controller.CommandLineController;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.request.UpdateVoucherRequest;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.domain.voucher.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.domain.voucher.model.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.PercentDiscountVoucher;
import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.repository.impl.JdbcVoucherRepository;
import org.promgrammers.springbootbasic.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("jdbc")
@SpringBootTest
class VoucherServiceTest {

    @MockBean
    CommandLineController controller;
    @Autowired
    private JdbcVoucherRepository voucherRepository;
    @Autowired
    private VoucherService voucherService;

    @BeforeAll
    void beforeAll() {
        voucherService = new VoucherService(voucherRepository);
    }

    @BeforeEach
    void beforeEach() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("생성 성공 - FixedVoucher")
    void createFixedVoucherTest() throws Exception {

        //given
        CreateVoucherRequest request = new CreateVoucherRequest(VoucherType.FIXED, 100L);

        //when
        voucherService.create(request);

        //then
        assertTrue(voucherRepository != null);
        assertThat(voucherRepository.findAll().size()).isEqualTo(1);
        assertThat(voucherRepository.findAll().get(0).getVoucherType()).isEqualTo(VoucherType.FIXED);
        assertThat(voucherRepository.findAll().get(0).getAmount()).isEqualTo(100L);
    }

    @Test
    @DisplayName("생성 성공 - PercentVoucher")
    void createPercentVoucherTest() throws Exception {

        //given
        CreateVoucherRequest request = new CreateVoucherRequest(VoucherType.PERCENT, 10L);

        //when
        voucherService.create(request);

        //then
        assertTrue(voucherRepository != null);
        assertThat(voucherRepository.findAll().size()).isEqualTo(1);
        assertThat(voucherRepository.findAll().get(0).getVoucherType()).isEqualTo(VoucherType.PERCENT);
        assertThat(voucherRepository.findAll().get(0).getAmount()).isEqualTo(10L);
    }

    @Test
    @DisplayName("전체 조회")
    void findAllVoucherTest() throws Exception {

        //given
        CreateVoucherRequest request1 = new CreateVoucherRequest(VoucherType.PERCENT, 10L);
        CreateVoucherRequest request2 = new CreateVoucherRequest(VoucherType.FIXED, 100L);
        List<CreateVoucherRequest> requestList = Arrays.asList(request1, request2);
        requestList.forEach(voucherService::create);

        //when
        VoucherListResponse voucherList = voucherService.findAll();

        //then
        assertNotNull(voucherList);
        List<VoucherResponse> voucherResponseList = voucherList.voucherResponseList();
        assertThat(voucherList.voucherResponseList()).hasSize(requestList.size());

        VoucherResponse firstResponse = voucherResponseList.get(0);
        assertThat(firstResponse.voucherType()).isEqualTo(request1.voucherType());
        assertThat(firstResponse.amount()).isEqualTo(request1.discountAmount());

        VoucherResponse secondResponse = voucherResponseList.get(1);
        assertThat(secondResponse.voucherType()).isEqualTo(request2.voucherType());
        assertThat(secondResponse.amount()).isEqualTo(request2.discountAmount());
    }

    @Test
    @DisplayName("단건 조회 성공 - 아이디가 존재하는 경우")
    void findOneSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long amount = 100L;
        Voucher voucher = new FixedAmountVoucher(voucherId, amount);
        voucherRepository.insert(voucher);

        //when
        Optional<VoucherResponse> findVoucher = voucherService.findById(voucher.getVoucherId());

        //then
        assertTrue(findVoucher.isPresent());
        assertThat(findVoucher.get().amount()).isEqualTo(amount);
        assertThat(findVoucher.get().voucherId()).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("단건 조회 실패 - 아이디가 존재하지 않는 경우")
    void findOneFailTest() throws Exception {

        // given
        UUID voucherId = UUID.randomUUID();

        // when -> then
        assertThrows(BusinessException.class, () -> voucherService.findById(voucherId));
    }

    @Test
    @DisplayName("업데이트 성공 - 해당 바우처가 존재할 때")
    void updateSuccessTest() throws Exception {

        //given
        UUID voucherId = UUID.randomUUID();
        long discount = 20;
        PercentDiscountVoucher voucher = new PercentDiscountVoucher(voucherId, discount);
        voucherRepository.insert(voucher);
        UpdateVoucherRequest updateRequest = new UpdateVoucherRequest(voucherId, voucher.getVoucherType(), 10);

        //when
        VoucherResponse updateVoucher = voucherService.update(updateRequest);
        //then
        assertThat(updateVoucher.voucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(updateVoucher.amount()).isEqualTo(10);
    }

    @Test
    @DisplayName("전체 삭제")
    void deleteAllTest() throws Exception {

        //given
        CreateVoucherRequest request1 = new CreateVoucherRequest(VoucherType.PERCENT, 10L);
        CreateVoucherRequest request2 = new CreateVoucherRequest(VoucherType.FIXED, 100L);
        List<CreateVoucherRequest> requestList = Arrays.asList(request1, request2);
        requestList.forEach(voucherService::create);

        //when
        voucherService.deleteAll();

        //then
        assertThat(voucherRepository.findAll().size()).isEqualTo(0);
    }
}