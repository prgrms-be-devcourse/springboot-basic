package org.promgrammers.springbootbasic.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.promgrammers.springbootbasic.domain.FixedAmountVoucher;
import org.promgrammers.springbootbasic.domain.Voucher;
import org.promgrammers.springbootbasic.domain.VoucherType;
import org.promgrammers.springbootbasic.dto.request.CreateVoucherRequest;
import org.promgrammers.springbootbasic.dto.response.VoucherListResponse;
import org.promgrammers.springbootbasic.dto.response.VoucherResponse;
import org.promgrammers.springbootbasic.repository.MemoryVoucherRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherServiceTest {

    private MemoryVoucherRepository voucherRepository;
    private VoucherService voucherService;

    @BeforeAll
    void beforeAll() {
        voucherRepository = new MemoryVoucherRepository();
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
        voucherService.createVoucher(request);

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
        voucherService.createVoucher(request);

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
        requestList.forEach(voucherService::createVoucher);

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
        assertThrows(IllegalArgumentException.class, () -> voucherService.findById(voucherId));
    }

    @Test
    @DisplayName("전체 삭제")
    void deleteAllTest() throws Exception {

        //given
        CreateVoucherRequest request1 = new CreateVoucherRequest(VoucherType.PERCENT, 10L);
        CreateVoucherRequest request2 = new CreateVoucherRequest(VoucherType.FIXED, 100L);
        List<CreateVoucherRequest> requestList = Arrays.asList(request1, request2);
        requestList.forEach(voucherService::createVoucher);

        //when
        voucherService.deleteAll();

        //then
        assertThat(voucherRepository.findAll().size()).isEqualTo(0);
    }
}