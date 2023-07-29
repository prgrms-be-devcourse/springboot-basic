package org.prgrms.kdt.voucher.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.dao.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.VoucherDetailResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.prgrms.kdt.voucher.service.dto.CreateVoucherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class VoucherServiceTest {

    @Autowired
    VoucherService voucherService;

    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeEach
    void setup() {
        /**
         * 테스트용 db에 바우처 3개 저장해 놓은 상태 (PERCENT 2개, FIXED 1개)
         */
        setupInsertVouchers();
    }

    @Test
    @DisplayName("바우처 생성 후 반환된 바우처의 amount 확인")
    void createVoucher_correctRequest_correctAmount() {
        //given
        CreateVoucherRequest request = new CreateVoucherRequest(VoucherType.FIXED, 50.0);

        //when
        VoucherResponse voucher = voucherService.createVoucher(request);

        //then
        double resultAmount = voucher.amount();
        assertThat(resultAmount).isEqualTo(50.0);
    }

    @Test
    @DisplayName("바우처 전체 조회하여 사이즈 확인")
    void findAll_correctSize() {
        //when
        VoucherResponses vouchers = voucherService.findAll();

        //then
        int resultSize = vouchers.vouchers().size();
        assertThat(resultSize).isEqualTo(3);
    }

    @Test
    @DisplayName("바우처id로 바우처 조회 후 반환받은 바우처 Id 확인")
    void findById_correctId(){
        //given
        UUID uuid = UUID.fromString("3c3dda5e-eb09-4b21-b57f-d9ef54bacd29");

        //when
        VoucherDetailResponse response = voucherService.findById(uuid);

        //then
        assertThat(response.voucherId()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("바우처Id로 바우처 한개 삭제 후 사이즈 확인")
    void deleteById_correctId(){
        //given
        UUID uuid = UUID.fromString("4267f1d3-8945-422b-88e0-dfef54756a37");

        //when
        voucherService.deleteById(uuid);

        //then
        int size = jdbcVoucherRepository.findAll().size();
        assertThat(size).isEqualTo(2);
    }

    @Test
    @DisplayName("바우처 타입으로 조건 조회 후 사이즈 확인")
    void findByType_correctType() {
        //when
        VoucherResponses responses = voucherService.findByType(VoucherType.PERCENT);

        //then
        int resultSize = responses.vouchers().size();
        assertThat(resultSize).isEqualTo(2);
    }

    private void setupInsertVouchers() {
        UUID voucherId1 = UUID.fromString("3c3dda5e-eb09-4b21-b57f-d9ef54bacd29");
        UUID voucherId2 = UUID.fromString("4267f1d3-8945-422b-88e0-dfef54756a37");
        UUID voucherId3 = UUID.fromString("5f237bf7-67b5-4175-bf02-2206ad28c2e2");
        jdbcVoucherRepository.insert(new Voucher(voucherId1, VoucherType.FIXED, VoucherType.FIXED.createPolicy(30.0), LocalDateTime.now()));
        jdbcVoucherRepository.insert(new Voucher(voucherId2, VoucherType.PERCENT, VoucherType.PERCENT.createPolicy(35.0), LocalDateTime.now()));
        jdbcVoucherRepository.insert(new Voucher(voucherId3, VoucherType.PERCENT, VoucherType.PERCENT.createPolicy(70.0), LocalDateTime.now()));
    }
}