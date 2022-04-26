package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.console.Command;
import com.waterfogsw.voucher.global.FrontController;
import com.waterfogsw.voucher.global.GetRequest;
import com.waterfogsw.voucher.global.MessageConverter;
import com.waterfogsw.voucher.global.PostRequest;
import com.waterfogsw.voucher.voucher.controller.VoucherController;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import com.waterfogsw.voucher.voucher.dto.RequestVoucherDto;
import com.waterfogsw.voucher.voucher.dto.Response;
import com.waterfogsw.voucher.voucher.dto.ResponseStatus;
import com.waterfogsw.voucher.voucher.dto.ResponseVoucherDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FrontControllerTests {
    @Mock
    private MessageConverter messageConverter;

    @Mock
    private VoucherController voucherController;

    @InjectMocks
    private FrontController frontController;

    @Nested
    @DisplayName("request 메서드는")
    class Describe_request {

        @Nested
        @DisplayName("null Request 가 전달되면")
        class Context_with_null {

            @Test
            @DisplayName("IllegalArgumentException 에러가 발생한다")
            void it_return_null() {
                assertThrows(IllegalArgumentException.class, () -> frontController.request(null));
            }
        }

        @Nested
        @DisplayName("create 명령의 데이터가 전달 되면")
        class Context_with_create {
            PostRequest requestMessage = new PostRequest(Command.CREATE, "FIXED_AMOUNT", "1000");

            @Test
            @DisplayName("생성한 데이터를 반환한다")
            void it_return_enum() {
                final var requestVoucherDto = new RequestVoucherDto(VoucherType.FIXED_AMOUNT, 1000);
                final var responseVoucherDto = new ResponseVoucherDto(VoucherType.FIXED_AMOUNT, 1000);
                final var response = new Response<>(responseVoucherDto, ResponseStatus.OK);

                when(messageConverter.convert(any(PostRequest.class))).thenReturn(requestVoucherDto);
                when(voucherController.voucherAdd(any(RequestVoucherDto.class))).thenReturn(response);

                String result = frontController.request(requestMessage);
                assertThat(result, is("ResponseVoucherDto[type=FIXED_AMOUNT, value=1000]"));
            }
        }

        @Nested
        @DisplayName("list 명령의 데이터가 전달 되면")
        class Context_with_findall {
            GetRequest requestMessage = new GetRequest(Command.LIST);

            @Test
            @DisplayName("모든 바우처에 대한 정보를 반환한다")
            void it_return_enum() {
                final var voucherDto1 = new ResponseVoucherDto(VoucherType.FIXED_AMOUNT, 1000);
                final var voucherDto2 = new ResponseVoucherDto(VoucherType.FIXED_AMOUNT, 2000);

                final List<ResponseVoucherDto> voucherDtoList = new ArrayList<>(
                        Arrays.asList(voucherDto1, voucherDto2)
                );

                final var response = new Response<>(voucherDtoList, ResponseStatus.OK);

                when(voucherController.voucherList()).thenReturn(response);

                String result = frontController.request(requestMessage);
                String expectedResult = "ResponseVoucherDto[type=FIXED_AMOUNT, value=1000]\nResponseVoucherDto[type=FIXED_AMOUNT, value=2000]";
                assertThat(result, is(expectedResult));
            }
        }
    }
}
