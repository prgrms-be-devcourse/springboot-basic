package org.devcourse.springbasic.global.io.console.voucher;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.devcourse.springbasic.global.io.output.voucher.VoucherOutput;

import java.text.MessageFormat;
import java.util.List;

public class VoucherConsoleOutput implements VoucherOutput {

    private final TextTerminal<?> terminal = TextIoFactory.getTextIO().getTextTerminal();

    @Override
    public void voucher(VoucherDto.ResponseDto voucher) {
        terminal.printf(MessageFormat.format("생성된 바우처 정보입니다 => {0}", voucher));
    }

    @Override
    public void vouchers(List<VoucherDto.ResponseDto> vouchers) {
        terminal.printf(MessageFormat.format("바우처 목록입니다\n {0}", vouchers));
    }

    @Override
    public void menus() {

        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.printf("\n\n=== Voucher Program ===\n" +
                "아래 메뉴 중 하나를 입력하세요.\n" +
                "1. exit (프로그램 종료)\n" +
                "2. create (새로운 바우처 생성)\n" +
                "3. list (모든 바우처 확인)\n");
    }

    @Override
    public void voucherMenus() {

        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();

        terminal.printf(
                "\n(바우처 선택) 아래 메뉴 중 하나를 입력하세요.\n" +
                        "1. Fixed amount voucher\n" +
                        "2. Percent discount voucher\n");
    }
}
