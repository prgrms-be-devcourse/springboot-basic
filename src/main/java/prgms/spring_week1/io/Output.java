package prgms.spring_week1.io;

import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.dto.VoucherOutputDto;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.io.message.ConsoleOutputMessage;

import java.util.List;
import java.util.logging.Logger;

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

        voucherList.stream()
                .map(i -> new VoucherOutputDto(i.getVoucherType(),i.getDiscount()))
                .forEach(System.out::println);
    }
    public void outputWarnMessage(String message) {
        System.out.println(message);
    }
}
