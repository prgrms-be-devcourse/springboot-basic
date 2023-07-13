package prgms.spring_week1.io;

import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.io.message.ConsoleOutputMessage;

import java.util.Collections;
import java.util.List;

public class Output {

    public void printBlackConsumerList(List<BlackConsumer> blackConsumerList) {
        if (blackConsumerList.isEmpty()) {
            System.out.println(ConsoleOutputMessage.EMPTY_BLACK_LIST_MESSAGE);
            return;
        }

        for (BlackConsumer blackConsumer : blackConsumerList) {
            System.out.println(blackConsumer.name() + " " + blackConsumer.age());
        }
    }

    public void printAllVoucher(List<Voucher> voucherList) {
        if (voucherList.equals(Collections.emptyList())) {
            System.out.println(ConsoleOutputMessage.NO_VOUCHER_LIST_MESSAGE);
            return;
        }

        for (Voucher voucher : voucherList) {
            System.out.println("상품권 종류 : 고정 가격 할인 상품권 " +
                    "할인 가격 :" + voucher.getDiscount() + "정보");
        }
    }
}
