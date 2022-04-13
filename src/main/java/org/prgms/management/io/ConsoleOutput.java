package org.prgms.management.io;

import org.prgms.management.blacklist.entity.Blacklist;
import org.prgms.management.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Component
public class ConsoleOutput implements Output {
    @Override
    public void init() {
        System.out.println("프로그램을 종료하려면 **exit**를 입력해주세요.");
        System.out.println("새로운 바우처를 만들려면 **create**를 입력해주세요.");
        System.out.println("바우처 목록을 보려면 **list**를 입력해주세요.");
        System.out.println("블랙리스트를 보려면 **blacklist**를 입력해주세요.\n");
    }

    @Override
    public void chooseVoucherType() {
        System.out.println("\n***바우처타입 바우처이름 할인율(액)***");
        System.out.println("위 정보를 순차적으로 입력해주세요.");
    }

    @Override
    public void voucherCreateSuccess() {
        System.out.println("\n바우처가 만들어졌습니다.\n");
    }

    @Override
    public void voucherCreateFail() {
        System.out.println("\n바우처 생성이 실패했습니다.\n");
    }

    @Override
    public void voucherList(Map<UUID, Voucher> voucherList) {
        System.out.println("\n=== 바우처 목록 ===");
        if (voucherList != null && !voucherList.isEmpty()) {
            voucherList.forEach((k, v) -> System.out.println(MessageFormat
                    .format("{0} {1} {2} {3}",
                            v.getVoucherId(),
                            v.getVoucherType(),
                            v.getVoucherName(),
                            v.getDiscountNum()
                    )
            ));
        }
        System.out.println();
    }

    @Override
    public void blackList(Map<UUID, Blacklist> blackList) {
        System.out.println("\n=== 블랙리스트 목록 ===");
        if (!blackList.isEmpty()) {
            blackList.forEach((k, v) -> System.out.println(MessageFormat
                    .format("{0} {1}", k, v)));
        }
        System.out.println();
    }
}
