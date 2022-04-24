package com.dojinyou.devcourse.voucherapplication;

import com.dojinyou.devcourse.voucherapplication.voucher.entity.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.VoucherController;
import com.dojinyou.devcourse.voucherapplication.voucher.entity.VoucherList;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VoucherApplication.class)
class ApplicationControllerTest {
    @Autowired
    ApplicationController applicationController;

    @MockBean
    VoucherController voucherController;

    @Nested
    @DisplayName("command Handle 함수에 관하여")
    class Describe_commandHandleMethod {
        @Nested
        @DisplayName("잘못된 명렁이 들어올 경우")
        class Context_Enter_Illegal_Command {
            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_Throws_Exception() {
                //given
                Command command = null;

                //when
                Throwable thrown = catchThrowable(()->applicationController.commandHandle(command));

                //then
                assertThat(thrown).isNotNull();
                assertThat(thrown).isInstanceOf(IllegalArgumentException.class);

            }
        }
        @Nested
        @DisplayName("사용자가 프로그램 종료 명령 입력 시,")
        class Context_Enter_Exit_Command {
            @Test
            @DisplayName("응답 데이터는 Command.Exit")
            void it_Return_Data_is_Command_Exit() {
                //given
                String userInput = "EXIT";
                Command command = Command.of(userInput);

                //when
                Response response = applicationController.commandHandle(command);

                //then
                assertThat(response).isNotNull();
                assertThat(response.getData()).isEqualTo(Command.EXIT);
            }
        }
        @Nested
        @DisplayName("사용자가 바우처 생성 명령 입력 시,")
        class Context_Enter_Create_Command {
            @Test
            @DisplayName("바우처 컨트롤러의 create 메소드가 호출된다.")
            void it_Call_create_method_of_VoucherController() {
                //given
                String userInput = "CREATE";
                Command command = Command.of(userInput);

                //when
                applicationController.commandHandle(command);

                //then
                verify(voucherController, atLeastOnce()).create(any());
            }

            @Test
            @Disabled //TODO: Console Test Case 별로 추후 추가
            @DisplayName("Voucher Type data를 가진 응답 객체를 반환한다.")
            void it_Return_Voucher_Data() {
                //given
                String userInput = "CREATE";
                Command command = Command.of(userInput);

                //when
                Response response = applicationController.commandHandle(command);

                //then
                assertThat(response).isNotNull();
                assertThat(response.getState()).isEqualTo(Response.State.SUCCESS);
                assertThat(response.getData()).isNotNull();
                assertThat(response.getData()).isInstanceOf(Voucher.class);
            }
        }
        @Nested
        @DisplayName("사용자가 바우처 조회 명령 입력 시,")
        class Context_Enter_List_Command {

            @Test
            @DisplayName("바우처 컨트롤러의 findAll 메소드가 호출된다.")
            void it_Call_findAll_method_of_VoucherController() {
                //given
                String userInput = "LIST";
                Command command = Command.of(userInput);

                //when
                applicationController.commandHandle(command);

                //then
                verify(voucherController, atLeastOnce()).findAll();
            }

            @Test
            @DisplayName("List<Voucher> Type data를 가진 응답 객체를 반환한다.")
            void it_Return_Voucher_Data() {
                //given
                String userInput = "LIST";
                Command command = Command.of(userInput);
                Response<VoucherList> expectedReturnObject = new Response<>(Response.State.SUCCESS, new VoucherList(Arrays.asList(new Voucher[]{})));
                when(voucherController.findAll()).thenReturn(expectedReturnObject);

                //when
                Response response = applicationController.commandHandle(command);

                //then
                assertThat(response).isNotNull();
                assertThat(response.getState()).isEqualTo(Response.State.SUCCESS);
                assertThat(response.getData()).isNotNull();
                assertThat(response.getData()).isInstanceOf(List.class);
            }
        }
    }
}