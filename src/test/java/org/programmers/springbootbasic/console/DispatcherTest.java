package org.programmers.springbootbasic.console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.console.command.Command;
import org.programmers.springbootbasic.console.handler.Handler;
import org.programmers.springbootbasic.console.model.Model;
import org.programmers.springbootbasic.console.model.ModelAndView;
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
    private static final Dispatcher DISPATCHER = new Dispatcher(CONSOLE_MAPPER_MOCK, DRAWER_MOCK);
    private static final ConsoleResponseCode CODE_AT_SUCCESS = PROCEED;

    @AfterEach
    void resetMock() {
        reset(CONSOLE_MAPPER_MOCK, DRAWER_MOCK);
    }

    @Test
    @DisplayName("정상 흐름")
    void respond() {
        var requestMock = mock(ConsoleRequest.class);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);

        var commandMock = mock(Command.class);
        when(requestMock.getCommand()).thenReturn(commandMock);
        var handlerMock = mock(Handler.class);
        when(CONSOLE_MAPPER_MOCK.getHandler(commandMock)).thenReturn(handlerMock);

        var modelAndViewMock = mock(ModelAndView.class);
        when(handlerMock.handleRequest(requestMock)).thenReturn(modelAndViewMock);
        when(DRAWER_MOCK.draw(modelAndViewMock)).thenReturn(CODE_AT_SUCCESS);

        var responseCode = DISPATCHER.respond(requestMock);

        var inOrder = inOrder(requestMock, CONSOLE_MAPPER_MOCK, handlerMock, DRAWER_MOCK);
        inOrder.verify(requestMock).getCommand();
        inOrder.verify(CONSOLE_MAPPER_MOCK).getHandler(commandMock);
        inOrder.verify(handlerMock).handleRequest(requestMock);
        inOrder.verify(DRAWER_MOCK).draw(modelAndViewMock);

        assertThat(responseCode, is(CODE_AT_SUCCESS));
    }

    @Test
    @DisplayName("예외 발생 흐름")
    void respondWithExceptionAtCommand() {
        var requestMock = mock(ConsoleRequest.class);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);

        var commandMock = mock(Command.class);
        when(requestMock.getCommand()).thenReturn(commandMock);
        var handlerMock = mock(Handler.class);
        when(CONSOLE_MAPPER_MOCK.getHandler(commandMock)).thenReturn(handlerMock);

        var modelAndViewMock = mock(ModelAndView.class);
        when(handlerMock.handleRequest(requestMock)).thenThrow(new RuntimeException());

        var responseCode = DISPATCHER.respond(requestMock);

        var inOrder = inOrder(requestMock, CONSOLE_MAPPER_MOCK, handlerMock, modelMock);
        inOrder.verify(requestMock).getCommand();
        inOrder.verify(CONSOLE_MAPPER_MOCK).getHandler(commandMock);
        inOrder.verify(handlerMock).handleRequest(requestMock);
        inOrder.verify(modelMock).setRedirectLink(ERROR);

        assertThat(responseCode, is(REDIRECT));
    }
}