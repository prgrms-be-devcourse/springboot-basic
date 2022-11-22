package org.prgrms.springbootbasic.type;

import org.prgrms.springbootbasic.processor.BlackListManagementProcessor;
import org.prgrms.springbootbasic.processor.CustomerProcessor;
import org.prgrms.springbootbasic.processor.Processor;
import org.prgrms.springbootbasic.processor.VoucherProcessor;

import java.util.Arrays;
import java.util.Optional;

public enum ServiceType {
    VOUCHER_SERVICE("1", VoucherProcessor.class),
    BLACK_LIST_MANAGEMENT_SERVICE("2", BlackListManagementProcessor.class),
    CUSTOMER_MANAGEMENT_SERVICE("3",CustomerProcessor .class);

    private final String number;
    private final Class<? extends Processor> processor;

    ServiceType(String number, Class<? extends Processor> processor) {
        this.number = number;
        this.processor = processor;
    }

    public static Class<? extends Processor> number2ProcessorClass(String number) {
        Optional<? extends Class<? extends Processor>> first = Arrays.stream(ServiceType.values())
                .filter(x -> x.number.equals(number))
                .map(x -> x.processor).findFirst();

        if (first.isEmpty()) {
            return null;
        }
        return first.get();
    }
}
