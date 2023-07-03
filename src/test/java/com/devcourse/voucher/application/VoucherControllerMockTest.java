 package com.devcourse.voucher.application;

import com.devcourse.voucher.application.dto.GetVoucherResponse;
import com.devcourse.voucher.presentation.VoucherController;
import com.devcourse.voucher.presentation.dto.ApplicationRequest;
import com.devcourse.voucher.presentation.dto.ApplicationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.devcourse.voucher.presentation.Command.CREATE;
import static com.devcourse.voucher.presentation.Command.EXIT;
import static com.devcourse.voucher.presentation.Command.LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class VoucherControllerMockTest {
    @InjectMocks
    private VoucherController voucherController;

    @Mock
    private VoucherService voucherService;

    @Test
    @DisplayName("생성 요청이 들어오면 전원이 켜져야 한다.")
    void createMapServiceTest() {
        // given
        ApplicationRequest request = ApplicationRequest.noPayload(CREATE);
        willDoNothing().given(voucherService).create(any());

        // when
        ApplicationResponse response = voucherController.generateResponse(request);

        // then
        then(voucherService).should(times(1)).create(any());
        assertThat(response.power()).isTrue();
    }

    @Test
    @DisplayName("조회 요청이 들어오면 전원이 켜지고 예상한 리스트를 반환해야 한다.")
    void listMapServiceTest() {
        // given
        ApplicationRequest request = ApplicationRequest.noPayload(LIST);
        List<GetVoucherResponse> expected = new ArrayList<>();
        given(voucherService.findAll()).willReturn(expected);

        // when
        ApplicationResponse response = voucherController.generateResponse(request);

        // then
        then(voucherService).should(times(1)).findAll();
        assertThat(response.power()).isTrue();
        assertThat(response.payload()).isEqualTo(expected);
    }

    @Test
    @DisplayName("종료 요청이 들어오면 전원이 꺼져야 한다.")
    void exitMapServiceTest() {
        // given
        ApplicationRequest request = ApplicationRequest.noPayload(EXIT);

        // when
        ApplicationResponse response = voucherController.generateResponse(request);

        // then
        assertThat(response.power()).isFalse();
    }
}
