package org.prgrms.springbootbasic.type;

import org.junit.jupiter.api.Test;
import org.prgrms.springbootbasic.processor.BlackListManagementProcessor;
import org.prgrms.springbootbasic.processor.Processor;
import org.prgrms.springbootbasic.processor.VoucherProcessor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

class ServiceTypeTest {

    @Test
    void testNumber2ProcessorClass_success() {
        Class<? extends Processor> serviceProcessorClass = ServiceType.number2ProcessorClass("1");
        Class<? extends Processor> blackListProcessorClas = ServiceType.number2ProcessorClass("2");
        assertThat(serviceProcessorClass, is(VoucherProcessor.class));
        assertThat(blackListProcessorClas, is(BlackListManagementProcessor.class));
    }

    @Test
    void testNumber2ProcessorClass_fail() {
        Class<? extends Processor> serviceProcessorClass = ServiceType.number2ProcessorClass("3");
        Class<? extends Processor> blackListProcessorClas = ServiceType.number2ProcessorClass("4");
        assertThat(serviceProcessorClass, nullValue(null));
        assertThat(blackListProcessorClas, nullValue(null));
    }
}