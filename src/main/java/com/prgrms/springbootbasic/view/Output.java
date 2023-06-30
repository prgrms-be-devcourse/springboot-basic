package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.domain.Voucher;
import java.util.Map;
import java.util.UUID;

public interface Output {

    //콘솔 명령어 출력
    void printlnCommand(String message);

    //생성할 바우처 타입 선택
    void printCreateVoucherType();

    //생성된 바우처 목록 출력
    void printlnVoucherList(Map<UUID, Voucher> voucherMap);

    //에러 메시지 출력
    void errorMessage(String errorMessage);
}
