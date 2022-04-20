package org.prgrms.kdt.model;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.model.function.Function;
import org.prgrms.kdt.service.BlackListService;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FunctionTest {

    @Test
    void doFunction() {
        VoucherService voucherService = mock(VoucherService.class);
        BlackListService blackListService = mock(BlackListService.class);
        CustomerService customerService = mock(CustomerService.class);

        Boolean exit = Function.exit.execute(voucherService, blackListService, customerService);

        assertThat(exit, is(true));
    }

}