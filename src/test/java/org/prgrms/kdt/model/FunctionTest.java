package org.prgrms.kdt.model;

import org.junit.jupiter.api.Test;
import org.prgrms.kdt.function.Function;
import org.prgrms.kdt.function.FunctionOperator;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class FunctionTest {

    @Test
    void doFunction() {
        FunctionOperator functionOperator = mock(FunctionOperator.class);

        Boolean exit = Function.exit.execute(functionOperator);

        assertThat(exit, is(true));
    }

}