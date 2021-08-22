package org.prgrms.kdt.application.voucher.io;

import org.prgrms.kdt.domain.voucher.domain.Voucher;

import java.util.Collection;

public interface Output {
    void printProgramName(); // 프로그램명

    void printCommandList(); // 명령어 목록

    void printInputError(); // 잘못된 명령어 입력

    void printExit(); // 프로그램 종료

    void printVoucherTypeList(); // 바우처 종류 목록

    void printVoucherCreateResult(boolean isCreated); // 바우처 생성 여부

    void printVoucherList(Collection<Voucher> vouchers); // 바우처 목록

    void printNoneVoucherList(); // 바우처 목록 없음
}
