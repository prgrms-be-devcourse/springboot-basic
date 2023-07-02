package prgms.spring_week1.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.io.message.ConsoleOutputMessage;

import java.util.List;

@Component
public class Output {
    private static final Logger log = LoggerFactory.getLogger(Output.class);

    public void outputMessage(String outputMessage){
        System.out.println(outputMessage);
    }

    public void printAllVoucher(List<Voucher> voucherList) {

    }

    public void printBlackConsumerList(List<BlackConsumer> blackConsumerList) {
        if (blackConsumerList.isEmpty()) {
            outputMessage(ConsoleOutputMessage.EMPTY_BLACK_LIST_MESSAGE);
            return;
        }
        blackConsumerList.forEach(bl -> System.out.println(bl.getName() + " " + bl.getAge()));
    }

    public void printDiscountFixedVoucherInfo(Voucher fixedAmountVoucher) {
        System.out.println("상품권 종류 : 고정 가격 할인 상품권 " +
                "할인 가격 :" + fixedAmountVoucher.getDiscount() + "원");
    }

    public void printDiscountAmountVoucherInfo(Voucher percentDiscountVoucher) {
        System.out.println("상품권 종류 : 고정 가격 할인 상품권 " +
                "할인률 :" + percentDiscountVoucher.getDiscount() + " 퍼센트");
    }
}
