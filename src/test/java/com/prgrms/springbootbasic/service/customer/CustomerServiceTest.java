package com.prgrms.springbootbasic.service.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    //해피 케이스 테스트
    @Test
    @DisplayName("고객 생성 테스트")
    void createCustomerTest() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("저장된 모든 고객 조회")
    void findByAllTest() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("고객 조회 - 생성일 순으로 조회")
    void findByCreatedAtTest() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("고객 조회 - id를 통해서 조회")
    void findByIdTest() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("고객 수정 테스트")
    void updateVoucherTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("고객 삭제 - id를 통해서 삭제")
    void deleteByIdTest() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("고객 삭제 - 저장된 모든 고객 삭제")
    void deleteByAllTest() {
        //given

        //when

        //then

    }


    //엣지 케이스
    @Test
    @DisplayName("고객 생성 실패  테스트 케이스")
    void createFailTest() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("모든 고객 조회 실패 테스트 케이스")
    void findAllFailTest() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("생성일 순 조회 실패 테스트 케이스")
    void findByCreatedAtFailTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("타입별 조회 실패 테스트 케이스")
    void findByTypeFailTest() {
        //given

        //when

        //then

    }

    @Test
    @DisplayName("id 조회 실패 테스트 케이스")
    void findByIdFailTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("수정 실패 테스트 케이스")
    void updateFailTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("id를 이용한 삭제 실패 테스트 케이스")
    void deleteByIdFailTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("모든 고객 삭제 실패 테스트 케이스")
    void deleteByAllFailTest() {
        //given

        //when

        //then
    }

    @Test
    @DisplayName("저장된 CustomerId 체크 실패 테스트 케이스")
    void existByIdFailTest() {
        //given

        //when

        //then
    }
}