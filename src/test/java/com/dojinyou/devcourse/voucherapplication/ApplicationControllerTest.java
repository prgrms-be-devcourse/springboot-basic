package com.dojinyou.devcourse.voucherapplication;

import com.dojinyou.devcourse.voucherapplication.voucher.VoucherController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VoucherApplication.class)
class ApplicationControllerTest {
    @Autowired
    ApplicationController applicationController;

    @MockBean
    VoucherController voucherController;

    @Test
    @DisplayName("Create 명령이 주어졌을 때, RequestHandle 함수를 호출하면 VoucherController의 RequestHandle 함수가 호출된다")
    void requestHandleWithCreateCommandTest() {
        //given
        Command userCommand = Command.CREATE;

        //when
        applicationController.requestHandle(userCommand);

        //then
        verify(voucherController, atLeastOnce()).requestHandle(userCommand);
    }

    @Test
    @DisplayName("List 명령이 주어졌을 때, RequestHandle 함수를 호출하면 VoucherController의 RequestHandle 함수가 호출된다")
    void requestHandleWithListCommandTest() {
        //given
        Command userCommand = Command.LIST;

        //when
        applicationController.requestHandle(userCommand);

        //then
        verify(voucherController, atLeastOnce()).requestHandle(userCommand);
    }

    @Test
    @DisplayName("Exit 명령이 주어졌을 때, RequestHandle 함수를 호출하면 Response 객체의 ")
    void requestHandleWithExitCommandTest() {
        //given
        Command userCommand = Command.LIST;

        //when
        Response response = applicationController.requestHandle(userCommand);

        //then
        assertThat(response.getData()).isEqualTo(Command.EXIT.toString());
    }

}