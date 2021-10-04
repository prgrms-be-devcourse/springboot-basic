package org.prgrms.dev.io;

import org.prgrms.dev.blacklist.domain.Blacklist;
import org.prgrms.dev.voucher.domain.Voucher;

import java.util.List;

public class OutputConsole implements Output {

    @Override
    public void init() {
        StringBuffer sb = new StringBuffer();
        sb.append("=== 바우처 프로그램 ===");
        sb.append(System.lineSeparator());
        sb.append("종료 [exit] ");
        sb.append(System.lineSeparator());
        sb.append("바우처 생성 [create] ");
        sb.append(System.lineSeparator());
        sb.append("바우처 할인정보 수정 [update] ");
        sb.append(System.lineSeparator());
        sb.append("바우처 삭제 [delete] ");
        sb.append(System.lineSeparator());
        sb.append("모든 바우처 조회 [list] ");
        System.out.println(sb);
    }

    @Override
    public void selectVoucherType() {
        StringBuffer sb = new StringBuffer();
        sb.append("=== 바우처 타입 선택 ===");
        sb.append(System.lineSeparator());
        sb.append("특정금액 할인 [fixed] |퍼센트 할인 [percent] ");
        System.out.print(sb);
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        voucherList.stream()
                .map(Voucher::toString)
                .forEach(System.out::println);
    }

    @Override
    public void printNotFound(String msg) {
        System.out.println(msg);
    }

    @Override
    public void printInvalidNumber(String msg) {
        System.out.println(msg + " 다시 입력해주세요.");
    }

    @Override
    public void printInvalidCommandType(String msg) {
        System.out.println(msg + " 다시 입력해주세요.");
    }

    @Override
    public void printInvalidVoucherType(String msg) {
        System.out.println(msg + " 다시 입력해주세요.");
    }

    @Override
    public void printBlackList(List<Blacklist> blackList) {
        blackList.stream()
                .map(Blacklist::toString)
                .forEach(System.out::println);
    }

}
