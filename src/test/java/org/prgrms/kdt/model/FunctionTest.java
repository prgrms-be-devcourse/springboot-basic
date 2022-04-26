package org.prgrms.kdt.model;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.function.VoucherProgramFunctions;
import org.prgrms.kdt.function.FunctionOperator;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FunctionTest {

    @Test
    void doFunction() {
        FunctionOperator functionOperator = mock(FunctionOperator.class);

        Boolean exit = VoucherProgramFunctions.exit.execute(functionOperator);

        assertThat(exit, is(true));
    }

}