package prgms.spring_week1.io;

import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.io.message.ConsoleOutputMessage;

import java.util.List;

public class Output {
    public void outputMessage(String outputMessage) {
        System.out.println(outputMessage);
    }

    public void printBlackConsumerList(List<BlackConsumer> blackConsumerList) {
        if (blackConsumerList.isEmpty()) {
            outputMessage(ConsoleOutputMessage.EMPTY_BLACK_LIST_MESSAGE);
            return;
        }

        for (BlackConsumer blackConsumer : blackConsumerList) {
            System.out.println(blackConsumer.name() + " " + blackConsumer.age());
        }
    }
    public void printAllVoucher(List<Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            this.outputMessage(ConsoleOutputMessage.NO_VOUCHER_LIST_MESSAGE);
            return;
        }

        for (Voucher voucher : voucherList) {
            if(voucher.getDiscount()<=100){
                System.out.println("상품권 종류 : 고정 할인률 상품권 " +
                        "할인률 :" + voucher.getDiscount() + " 퍼센트");
                continue;
            }
            System.out.println("상품권 종류 : 고정 가격 할인 상품권 " +
                    "할인 가격 :" + voucher.getDiscount() + "원");
        }
    }

    public void outputWarnMessage(String message) {
        System.out.println(message);
    }
}
