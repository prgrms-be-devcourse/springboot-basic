package com.programmers.application.controller.api;

import com.programmers.application.controller.AcceptanceTestExecutionListener;
import com.programmers.application.dto.reponse.VoucherInfoResponse;
import com.programmers.application.dto.request.RequestFactory;
import com.programmers.application.dto.request.VoucherCreationRequest;
import com.programmers.application.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class,}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class VoucherApiControllerTest {
    private static final int PERCENT_DISCOUNT_AMOUNT = 10;
    private static final int FIXED_DISCOUNT_AMOUNT = 100;
    private static final String FIXED_AMOUNT_VOUCHER_TYPE = "fixed";
    private static final String PERCENT_DISCOUNT_VOUCHER_TYPE = "percent";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private VoucherService voucherService;

    @DisplayName("바우처 생성 및 저장 시, 클라이언트에서 Get, /api/v1/vouchers 요청을 하면, 저장된 바우처들을 응답한다.")
    @Test
    void findVouchersTest() {
        final int expectedCount = 2;

        //given
        VoucherCreationRequest voucherCreationRequest1 = RequestFactory.createVoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        UUID voucher1Id = voucherService.createVoucher(voucherCreationRequest1);
        VoucherCreationRequest voucherCreationRequest2 = RequestFactory.createVoucherCreationRequest(PERCENT_DISCOUNT_VOUCHER_TYPE, PERCENT_DISCOUNT_AMOUNT);
        UUID voucher2Id = voucherService.createVoucher(voucherCreationRequest2);

        //when
        String url = "http://localhost:" + port + "/api/v1/vouchers";
        ResponseEntity<VoucherInfoResponse[]> response = testRestTemplate.getForEntity(url, VoucherInfoResponse[].class);
        VoucherInfoResponse[] vouchers = response.getBody();

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(vouchers).hasSize(expectedCount);
        assertThat(vouchers[0].voucherId()).isEqualTo(voucher1Id);
        assertThat(vouchers[0].discountAmount()).isEqualTo(FIXED_DISCOUNT_AMOUNT);
        assertThat(vouchers[1].voucherId()).isEqualTo(voucher2Id);
        assertThat(vouchers[1].discountAmount()).isEqualTo(PERCENT_DISCOUNT_AMOUNT);
    }

    @DisplayName("바우처 생성 및 저장 시, 클라이언트에서 Get, /api/v1/voucher/{voucherId} 요청을 하면, 해당 voucherId로 바우처를 찾아 응답한다.")
    @Test
    void findVoucherByVoucherIdTest() {
        //given
        VoucherCreationRequest voucherCreationRequest = RequestFactory.createVoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);
        UUID voucherId = voucherService.createVoucher(voucherCreationRequest);

        //when
        String url = "http://localhost:" + port + "/api/v1/voucher/{voucherId}";
        ResponseEntity<VoucherInfoResponse> response = testRestTemplate.getForEntity(url, VoucherInfoResponse.class, voucherId);
        VoucherInfoResponse voucher = response.getBody();

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(voucher.voucherId()).isEqualTo(voucherId);
        assertThat(voucher.discountAmount()).isEqualTo(FIXED_DISCOUNT_AMOUNT);
    }

    @DisplayName("클라이언트에서 바우처 타입과 할인양의 정보와 함께 Post, /api/v1/voucher 요청을 하면, 해당 정보를 갖고 바우처를 생성한다.")
    @Test
    void createVoucherTest() {
        //given
        VoucherCreationRequest request = RequestFactory.createVoucherCreationRequest(FIXED_AMOUNT_VOUCHER_TYPE, FIXED_DISCOUNT_AMOUNT);

        //when
        String url = "http://localhost:" + port + "/api/v1/voucher";
        ResponseEntity<VoucherInfoResponse> response = testRestTemplate.postForEntity(url, request, VoucherInfoResponse.class);
        VoucherInfoResponse voucher = response.getBody();

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(voucher.discountAmount()).isEqualTo(FIXED_DISCOUNT_AMOUNT);
    }
}
