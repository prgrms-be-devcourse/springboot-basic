package org.programmers.springbootbasic.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.handler.Handler;
import org.programmers.springbootbasic.console.model.Model;
import org.programmers.springbootbasic.console.model.ModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleMapper;
import org.programmers.springbootbasic.console.request.ConsoleRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.REDIRECT;
import static org.programmers.springbootbasic.console.command.RedirectCommand.ERROR;

class DispatcherTest {

    private static final ConsoleMapper CONSOLE_MAPPER_MOCK = mock(ConsoleMapper.class);
    private static final Drawer DRAWER_MOCK = mock(Drawer.class);
    private static final ConsoleResponseCode CODE_AT_SUCCESS = PROCEED;
    private static final ConsoleRequest REQUEST_MOCK = mock(ConsoleRequest.class);
    private static final Model MODEL_MOCK = mock(Model.class);
    private static final Command COMMAND_MOCK = mock(Command.class);
    private static final Handler HANDLER_MOCK = mock(Handler.class);

    private static final Dispatcher DISPATCHER = new Dispatcher(CONSOLE_MAPPER_MOCK, DRAWER_MOCK);

    @AfterEach
    void resetMock() {
        reset(CONSOLE_MAPPER_MOCK, DRAWER_MOCK, REQUEST_MOCK, MODEL_MOCK, COMMAND_MOCK, HANDLER_MOCK);
    }

    @BeforeEach
    void commonMockSettings() {
        when(REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);
        when(REQUEST_MOCK.getCommand()).thenReturn(COMMAND_MOCK);
        when(CONSOLE_MAPPER_MOCK.getHandler(COMMAND_MOCK)).thenReturn(HANDLER_MOCK);
    }

    @Test
    @DisplayName("정상 흐름")
    void respond() {
        var modelAndViewMock = mock(ModelAndView.class);
        when(HANDLER_MOCK.handleRequest(REQUEST_MOCK)).thenReturn(modelAndViewMock);
        when(DRAWER_MOCK.draw(modelAndViewMock)).thenReturn(CODE_AT_SUCCESS);

        var responseCode = DISPATCHER.respond(REQUEST_MOCK);

        var inOrder = inOrder(REQUEST_MOCK, CONSOLE_MAPPER_MOCK, HANDLER_MOCK, DRAWER_MOCK);
        inOrder.verify(REQUEST_MOCK).getCommand();
        inOrder.verify(CONSOLE_MAPPER_MOCK).getHandler(COMMAND_MOCK);
        inOrder.verify(HANDLER_MOCK).handleRequest(REQUEST_MOCK);
        inOrder.verify(DRAWER_MOCK).draw(modelAndViewMock);

        assertThat(responseCode, is(CODE_AT_SUCCESS));
    }

    @Test
    @DisplayName("예외 발생 흐름")
    void respondWithExceptionAtCommand() {
        when(HANDLER_MOCK.handleRequest(REQUEST_MOCK)).thenThrow(new RuntimeException());

        var responseCode = DISPATCHER.respond(REQUEST_MOCK);

        var inOrder = inOrder(REQUEST_MOCK, CONSOLE_MAPPER_MOCK, HANDLER_MOCK, MODEL_MOCK);
        inOrder.verify(REQUEST_MOCK).getCommand();
        inOrder.verify(CONSOLE_MAPPER_MOCK).getHandler(COMMAND_MOCK);
        inOrder.verify(HANDLER_MOCK).handleRequest(REQUEST_MOCK);
        inOrder.verify(MODEL_MOCK).setRedirectLink(ERROR);

        assertThat(responseCode, is(REDIRECT));
    }
}