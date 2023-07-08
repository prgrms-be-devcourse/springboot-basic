package prgms.spring_week1.io;

import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.dto.VoucherOutputDto;
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

    public void printVoucherInfo(VoucherOutputDto voucherOutputDto) {
        System.out.println(voucherOutputDto);
    }

}
