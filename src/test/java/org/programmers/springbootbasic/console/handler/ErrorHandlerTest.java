package org.programmers.springbootbasic.console.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.console.ConsoleProperties;
import org.programmers.springbootbasic.console.SimpleErrorMessageMapper;
import org.programmers.springbootbasic.console.command.RedirectCommand;
import org.programmers.springbootbasic.console.model.Model;
import org.programmers.springbootbasic.console.model.ModelAndView;
import org.programmers.springbootbasic.console.request.ConsoleRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.console.command.RedirectCommand.*;

class ErrorHandlerTest {

    private static final ConsoleProperties CONSOLE_PROPERTIES_MOCK = mock(ConsoleProperties.class);
    private static final SimpleErrorMessageMapper SIMPLE_ERROR_MESSAGE_MAPPER_MOCK = mock(SimpleErrorMessageMapper.class);
    private static final ErrorHandler ERROR_HANDLER = new ErrorHandler(CONSOLE_PROPERTIES_MOCK, SIMPLE_ERROR_MESSAGE_MAPPER_MOCK);

    @AfterEach
    void resetMock() {
        reset(CONSOLE_PROPERTIES_MOCK, SIMPLE_ERROR_MESSAGE_MAPPER_MOCK);
    }

    @Test
    @DisplayName("오류 처리: 세부 정보 표기")
    void handleDetailErrorRequest() {
        var requestMock = mock(ConsoleRequest.class);
        when(requestMock.getCommand()).thenReturn(ERROR);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);

        var exception = new RuntimeException();
        when(modelMock.getAttributes("errorData")).thenReturn(exception);

        when(CONSOLE_PROPERTIES_MOCK.isDetailErrorMessage()).thenReturn(true);

        var modelAndView = ERROR_HANDLER.handleRequest(requestMock);

        verify(modelMock).addAttributes("errorMessage", exception.toString());
        verify(modelMock).addAttributes("errorName", exception.getClass());

        assertThat(modelAndView.getView(), is(requestMock.getCommand().getViewName()));
    }

    @Test
    @DisplayName("오류 처리: 단순화된 정보 표기")
    void handleSimpleErrorRequest() {
        var requestMock = mock(ConsoleRequest.class);
        when(requestMock.getCommand()).thenReturn(ERROR);
        var modelMock = mock(Model.class);
        when(requestMock.getModel()).thenReturn(modelMock);

        var exception = new RuntimeException();
        when(modelMock.getAttributes("errorData")).thenReturn(exception);

        when(CONSOLE_PROPERTIES_MOCK.isDetailErrorMessage()).thenReturn(false);

        var errorDataMock = new SimpleErrorMessageMapper.ErrorData("에러명", "에러 메시지");
        when(SIMPLE_ERROR_MESSAGE_MAPPER_MOCK.toErrorData(exception)).thenReturn(errorDataMock);

        var modelAndView = ERROR_HANDLER.handleRequest(requestMock);

        verify(modelMock).addAttributes("errorMessage", errorDataMock.message());
        verify(modelMock).addAttributes("errorName", errorDataMock.name());

        assertThat(modelAndView.getView(), is(requestMock.getCommand().getViewName()));
    }
}