package kr.co.programmers.springbootbasic.io;


import kr.co.programmers.springbootbasic.dto.VoucherResponseDto;
import kr.co.programmers.springbootbasic.voucher.VoucherType;

import java.util.List;

public interface Output {
    void printProgramMenu();

    void printCreationMenu();

    void printAmountEnterMessage(VoucherType voucherType);

    void printMessage(String message);

    void printMessage(VoucherResponseDto dto);

    void printMessage(List<VoucherResponseDto> lists);

    void printExit();
}
