package com.prgrms.devkdtorder.voucher.controller.api;

import com.prgrms.devkdtorder.customer.repository.FileCustomerRepository;
import com.prgrms.devkdtorder.exception.GlobalExceptionHandler;
import com.prgrms.devkdtorder.util.ApiUtils;
import com.prgrms.devkdtorder.util.ApiUtils.ApiError;
import com.prgrms.devkdtorder.util.ApiUtils.ApiResponse;
import com.prgrms.devkdtorder.voucher.domain.Voucher;
import com.prgrms.devkdtorder.voucher.domain.VoucherType;
import com.prgrms.devkdtorder.voucher.dto.VoucherDto;
import com.prgrms.devkdtorder.voucher.repository.FileVoucherRepository;
import com.prgrms.devkdtorder.voucher.repository.JdbcVoucherRepository;
import com.prgrms.devkdtorder.voucher.service.VoucherService;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
@Slf4j
class VoucherApiE2ETest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private VoucherService voucherService;

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setUp() {
        log.info("setUp called...");
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
                .start();

    }

    @AfterAll
    static void cleanUp() {
        log.info("cleanUp called...");
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("바우처 저장 성공 테스트")
    void testSaveVoucher() {
        //given
        Voucher fixedAmountVoucher = VoucherType.FIXEDAMOUNT.createVoucher(
                UUID.randomUUID(),
                1000,
                "추석맞이 천원 할인 쿠폰",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        VoucherDto voucherDto = new VoucherDto(fixedAmountVoucher);

        RequestEntity<VoucherDto> request = RequestEntity.post(URI.create("/api/v1/vouchers"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(voucherDto);
        //when
        var response = testRestTemplate.exchange(request, new ParameterizedTypeReference<ApiResponse<Boolean>>() {});
        //then
        SoftAssertions.assertSoftly(soft -> {
            assertSuccess(response);
            Boolean data = response.getBody().getData();
            soft.assertThat(data).isTrue();
        });
    }

    @Test
    @DisplayName("바우처 ID 조회 성공 테스트")
    void testFindVoucherById() {
        //given
        UUID savedVoucherId = UUID.randomUUID();
        Voucher fixedAmountVoucher = VoucherType.FIXEDAMOUNT.createVoucher(
                savedVoucherId,
                1000,
                "추석맞이 천원 할인 쿠폰",
                LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        voucherService.saveVoucher(fixedAmountVoucher);

        //when
        RequestEntity<Void> request = RequestEntity.get(URI.create("/api/v1/vouchers/" + savedVoucherId))
                .accept(MediaType.APPLICATION_JSON)
                .build();

        var response = testRestTemplate.exchange(request, new ParameterizedTypeReference<ApiResponse<VoucherDto>>() {});
        //then
        SoftAssertions.assertSoftly(soft -> {
            assertSuccess(response);
            VoucherDto actualVoucher = response.getBody().getData();
            soft.assertThat(actualVoucher.getVoucherId()).isEqualTo(savedVoucherId);
        });
    }

    @Test
    @DisplayName("바우처 ID 조회 실패 테스트 - 저장되지 않은 ID로 조회")
    void testFindVoucherByIdFail() {
        //given
        String findVoucherId = "0ee56205-ea1f-4925-9923-a07f3a2d1322";
        //when
        RequestEntity<Void> request = RequestEntity.get(URI.create("/api/v1/vouchers/" + findVoucherId))
                .accept(MediaType.APPLICATION_JSON)
                .build();
        var response = testRestTemplate.exchange(request, new ParameterizedTypeReference<ApiResponse<VoucherDto>>() {});
        //then
        assertError(response);
        ApiError error = response.getBody().getError();
        assertThat(error.getStatus()).isEqualTo(400);
        assertThat(error.getMessage()).isEqualTo("Can not find a voucher for 0ee56205-ea1f-4925-9923-a07f3a2d1322");
    }

    private <T> void assertSuccess(ResponseEntity<ApiResponse<T>> response) {
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            soft.assertThat(response.getBody()).isNotNull();
            soft.assertThat(response.getBody().isSuccess()).isTrue();
            soft.assertThat(response.getBody().getError()).isNull();
            soft.assertThat(response.getBody().getData()).isNotNull();
        });
    }

    private <T> void assertError(ResponseEntity<ApiResponse<T>> response) {
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            soft.assertThat(response.getBody()).isNotNull();
            soft.assertThat(response.getBody().isSuccess()).isFalse();
            soft.assertThat(response.getBody().getError()).isNotNull();
            soft.assertThat(response.getBody().getData()).isNull();
        });
    }
}

@SpringBootApplication
@ComponentScan(basePackages = "com.prgrms.devkdtorder",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JdbcVoucherRepository.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoucherService.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = GlobalExceptionHandler.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = VoucherApiController.class)},
        useDefaultFilters = false
)
class TestApplication {

}
