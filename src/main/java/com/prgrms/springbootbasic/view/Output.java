package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.dto.customer.response.CustomerListResponse;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;

public interface Output {

    void printConsoleMenu();

    //바우처 관련 메뉴
    void printVoucherMenu();

    //바우처 생성 - 타입
    void printVoucherCreateTypeMenu();

    //바우처 생성 - 금액
    void printVoucherCreateDiscountMenu();

    //바우처 조회 - 메뉴
    void printVoucherSelectMenu();

    //바우처 조회 - 모든
    void printVoucherSelectBYAll(VoucherListResponse voucherListResponse);

    //바우처 조회 - 타입별
    void printVoucherSelectByTypeList(VoucherListResponse voucherListResponse);

    void printVoucherSelectByType();

    //바우처 조회 - ID조회

    void printVoucherSelectById();

    //바우처 조회 - 생성기간
    void printVoucherSelectByCreateAt(VoucherListResponse voucherListResponse);

    //바우처 변경 - 메뉴
    void printVoucherUpdateMenu();

    //바우처 변경 - Id
    void printVoucherUpdateById();

    //바우처 삭제 - 메뉴
    void printVoucherDeleteMenu();

    //바우처 삭제 - ID
    void printVoucherDeleteById();

    //바우처 삭제 - 모든 바우처 삭제
    void printVoucherDeleteAll();

    //고객 관련 작업 메뉴
    void printCustomerMenu();

    //고객 생성 - 안내
    void printCustomerCreateMenu();

    //고객 조회 - 메뉴
    void printCustomerSelectMenu();

    //고객 조회 - 모든
    void printCustomerSelectAll(CustomerListResponse customerListResponse);

    //고객 조회 - ID
    void printCustomerSelectById();

    //고객 조회 - 생성일
    void printCutomerSelectByCreatedAt();


    //고객 변경 -  ID
    void printCustomerUpdateByID();

    //고객 삭제 - 메뉴
    void printCustomerDeleteMenu();

    //고객 삭제 - ID
    void printCustomerDeleteByID();

    //고객 삭제 - ALL
    void printCustomerDeleteByAll();


    //Exit시 출력문
    void printExitMessage();

    //에러 메시지 출력문
    void printErrorMessage(String message);

    //작업 완료문
    void printCompleteMessage();
}
