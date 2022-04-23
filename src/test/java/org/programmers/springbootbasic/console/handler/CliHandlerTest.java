package org.programmers.springbootbasic.console.handler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.programmers.springbootbasic.console.ConsoleResponseCode;
import org.programmers.springbootbasic.console.command.InputCommand;
import org.programmers.springbootbasic.console.model.Model;
import org.programmers.springbootbasic.console.model.ModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.programmers.springbootbasic.console.request.RequestCreator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.*;
import static org.programmers.springbootbasic.console.command.InputCommand.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class CliHandlerTest {

    private static final CliHandler cliHandler = new CliHandler();

    @BeforeAll
    static void initializeHandler() {
        cliHandler.initCommandList();
    }

    @Test
    @DisplayName("명령어: HOME, 정적 페이지 요청")
    void handleStaticPageRequest() {
        ConsoleRequest request = mock(ConsoleRequest.class);
        when(request.getCommand()).thenReturn(HOME);

        ModelAndView modelAndView = cliHandler.handleRequest(request);
        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }

    @Test
    @DisplayName("명령어: HELP")
    void handleHelpRequest() {
        ConsoleRequest request = mock(ConsoleRequest.class);
        when(request.getCommand()).thenReturn(HELP);
        var model = new Model();
        when(request.getModel()).thenReturn(model);

        ModelAndView modelAndView = cliHandler.handleRequest(request);
        assertThat(modelAndView.getResponseCode(), is(PROCEED));

        assertThat(((List<String>)model.getAttributes("allCommandsInformation")).size(),
                is(InputCommand.values().length));
    }

    @Test
    @DisplayName("명령어: EXIT")
    void handleExitRequest() {
        ConsoleRequest request = mock(ConsoleRequest.class);
        when(request.getCommand()).thenReturn(EXIT);

        ModelAndView modelAndView = cliHandler.handleRequest(request);
        assertThat(modelAndView.getResponseCode(), is(STOP));
    }
}