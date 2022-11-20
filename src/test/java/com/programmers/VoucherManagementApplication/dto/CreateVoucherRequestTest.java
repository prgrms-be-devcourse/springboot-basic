package com.programmers.VoucherManagementApplication.dto;

import com.programmers.VoucherManagementApplication.vo.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// methodsource
class CreateVoucherRequestTest {

    @Test
    @DisplayName("요청한 입력값이 올바른 경우")
    void 바우처타입_올바른_경우() {
        // given
        String requestFixed = "fixed 10";
        String requestPercent = "percent 50";

        // when
        CreateVoucherRequest createFixRequest = new CreateVoucherRequest(requestFixed);
        CreateVoucherRequest createPercentRequest = new CreateVoucherRequest(requestPercent);

        // then
        assertEquals(createFixRequest.getVoucherType(), VoucherType.FIXED_DISCOUNT);
        assertEquals(createPercentRequest.getVoucherType(), VoucherType.PERCENT_DISCOUNT);
    }

    @Test
    @DisplayName("요청한 입력값의 사이즈가 2가 아닌 경우")
    public void 입력값_사이즈_올바르지않은_경우() {
        // given
        String request1 = "percent10";
        String request2 = "fixed 10 30";

        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(request1));
        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(request2));
    }


    @Test
    @DisplayName("입력한 할인값이 숫자가 아닐때")
    //@ParameterizedTest, methodSource...
    public void 입력한_할인값_숫자_아님() {
        String requestFixed = "fixed abc";
        String requestPercent = "percent -!@3";

        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(requestFixed));
        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(requestPercent));
    }

    @Test
    @DisplayName("할인값이 0인 경우")
    public void 할인값_0인_경우() {
        String requestFixed = "fixed 0";
        String requestPercent = "percent 0";

        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(requestFixed));
        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(requestPercent));
    }

    @Test
    @DisplayName("할인값이 음수인 경우")
    public void 할인값_음수인_경우() {
        String requestFixed = "fixed -100";
        String requestPercent = "percent -10";

        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(requestFixed));
        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(requestPercent));
    }

    @Test
    @DisplayName("바우처타입이 올바르지 않은 경우")
    public void 바우처타입_올바르지_않은_경우() {
        String request1 = "abc 100";
        String request2 = "1 100";

        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(request1));
        assertThrows(IllegalArgumentException.class, () -> new CreateVoucherRequest(request2));
    }
}