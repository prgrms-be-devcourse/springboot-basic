package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;

public interface Output {

    void printConsoleMenu();

    //바우처 관련
    void printVoucherMenu();

    //바우처 생성 - 타입
    void printVoucherCreateTypeMenu();

    //바우처 생성 - 금액
    void printVoucherCreateDiscountMenu();

    //바우처 조회 - 메뉴
    void printVoucherSelectMenu();

    //바우처 조회 - 모든
    void printVoucherSelectAll(VoucherListResponse voucherListResponse);

    //바우처 조회 - 타입별
    void printVoucherSelectTypeList(VoucherListResponse voucherListResponse);

    void printVoucherSelectType();

    //바우처 조회 - ID조회

    void printVoucherSelectId();

    //바우처 조회 - 생성기간
    void printVoucherSelectCreateAt();

    //바우처 변경 - 메뉴
    void printVoucherUpdateMenu();

    //바우처 변경 - Id
    void printVoucherUpdateId();

    //바우처 삭제 - ID
    void printVoucherDeleteMenu();

    //바우처 삭제 - 모든 바우처 삭제
    void printVoucherDeleteAll();

    //고객 관련
    void printCustomerMenu();

    //Exit시 출력문
    void printExitMessage();

    //에러 메시지 출력문
    void printErrorMessage(String message);

    //작업 완료문
    void printCompleteMessage();

}
