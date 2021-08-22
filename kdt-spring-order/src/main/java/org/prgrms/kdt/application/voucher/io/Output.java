package org.prgrms.kdt.application.voucher.io;

import org.prgrms.kdt.domain.voucher.domain.Voucher;

import java.util.Collection;

public interface Output {
    void printProgramName(); // 프로그램명

    void printCommandList(); // 명령어 목록

    void printInputCommandError(); // 잘못된 명령어 입력

    void printExit(); // 프로그램 종료

    void printVoucherList(Collection<Voucher> vouchers); // 바우처 목록

    void printNoneVoucherList(); // 바우처 목록 없음
}
