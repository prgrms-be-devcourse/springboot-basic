package org.programmers.springbootbasic.console.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.console.model.ConsoleModel;
import org.programmers.springbootbasic.console.model.ConsoleModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleRequest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.STOP;
import static org.programmers.springbootbasic.console.command.InputCommand.*;

class CliHandlerTest {

    private static final ConsoleRequest REQUEST_MOCK = mock(ConsoleRequest.class);

    private static final CliHandler CLI_HANDLER = new CliHandler();

    @AfterEach
    void resetMock() {
        reset(REQUEST_MOCK);
    }

    @BeforeAll
    static void initializeHandler() {
        CLI_HANDLER.initCommandList();
    }

    @Test
    @DisplayName("명령어: HOME, 정적 페이지 요청")
    void handleStaticPageRequest() {
        when(REQUEST_MOCK.getCommand()).thenReturn(HOME);

        ConsoleModelAndView consoleModelAndView = CLI_HANDLER.handleRequest(REQUEST_MOCK);
        assertThat(consoleModelAndView.getResponseCode(), is(PROCEED));
    }

    @Test
    @DisplayName("명령어: HELP")
    void handleHelpRequest() {
        when(REQUEST_MOCK.getCommand()).thenReturn(HELP);
        var model = new ConsoleModel();
        when(REQUEST_MOCK.getConsoleModel()).thenReturn(model);

        ConsoleModelAndView consoleModelAndView = CLI_HANDLER.handleRequest(REQUEST_MOCK);
        assertThat(consoleModelAndView.getResponseCode(), is(PROCEED));

        assertThat(((List<String>) model.getAttributes("allCommandsInformation")).size(),
                is(values().length));
    }

    @Test
    @DisplayName("명령어: EXIT")
    void handleExitRequest() {
        when(REQUEST_MOCK.getCommand()).thenReturn(EXIT);

        ConsoleModelAndView consoleModelAndView = CLI_HANDLER.handleRequest(REQUEST_MOCK);
        assertThat(consoleModelAndView.getResponseCode(), is(STOP));
    }
}