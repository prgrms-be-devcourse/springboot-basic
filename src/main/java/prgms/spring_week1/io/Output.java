package prgms.spring_week1.io;

import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
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
        blackConsumerList.forEach(bl -> System.out.println(bl.name() + " " + bl.age()));
    }

    public void classifyVoucherInfo(VoucherType voucherType,long discountAmount) {
        switch (voucherType){
            case FIXED -> printDiscountFixedVoucherInfo(discountAmount);
            case PERCENT -> printDiscountAmountVoucherInfo(discountAmount);
        }
    }

    public void printDiscountFixedVoucherInfo(long fixedAmount) {
        System.out.println("상품권 종류 : 고정 가격 할인 상품권 " +
                "할인 가격 :" + fixedAmount + "원");
    }

    public void printDiscountAmountVoucherInfo(long percentDiscount) {
        System.out.println("상품권 종류 : 고정 가격 할인 상품권 " +
                "할인률 :" + percentDiscount + " 퍼센트");
    }
}
