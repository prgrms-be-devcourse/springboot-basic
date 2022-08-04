package org.programmers.springbootbasic.console.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.console.ConsoleProperties;
import org.programmers.springbootbasic.console.SimpleErrorMessageMapper;
import org.programmers.springbootbasic.console.model.ConsoleModel;
import org.programmers.springbootbasic.console.request.ConsoleRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.console.command.RedirectCommand.ERROR;

class ErrorHandlerTest {

    private static final ConsoleProperties CONSOLE_PROPERTIES_MOCK = mock(ConsoleProperties.class);
    private static final SimpleErrorMessageMapper SIMPLE_ERROR_MESSAGE_MAPPER_MOCK = mock(SimpleErrorMessageMapper.class);
    private static final ConsoleRequest REQUEST_MOCK = mock(ConsoleRequest.class);
    private static final ConsoleModel CONSOLE_MODEL_MOCK = mock(ConsoleModel.class);

    private static final ErrorHandler ERROR_HANDLER = new ErrorHandler(CONSOLE_PROPERTIES_MOCK, SIMPLE_ERROR_MESSAGE_MAPPER_MOCK);

    @AfterEach
    void resetMock() {
        reset(CONSOLE_PROPERTIES_MOCK, SIMPLE_ERROR_MESSAGE_MAPPER_MOCK, REQUEST_MOCK, CONSOLE_MODEL_MOCK);
    }

    @Test
    @DisplayName("오류 처리: 세부 정보 표기")
    void handleDetailErrorRequest() {
        when(REQUEST_MOCK.getCommand()).thenReturn(ERROR);
        when(REQUEST_MOCK.getConsoleModel()).thenReturn(CONSOLE_MODEL_MOCK);

        var exception = new RuntimeException();
        when(CONSOLE_MODEL_MOCK.getAttributes("errorData")).thenReturn(exception);

        when(CONSOLE_PROPERTIES_MOCK.isDetailErrorMessage()).thenReturn(true);

        var modelAndView = ERROR_HANDLER.handleRequest(REQUEST_MOCK);

        verify(CONSOLE_MODEL_MOCK).addAttributes("errorMessage", exception.toString());
        verify(CONSOLE_MODEL_MOCK).addAttributes("errorName", exception.getClass());

        assertThat(modelAndView.getView(), is(REQUEST_MOCK.getCommand().getViewName()));
    }

    @Test
    @DisplayName("오류 처리: 단순화된 정보 표기")
    void handleSimpleErrorRequest() {
        when(REQUEST_MOCK.getCommand()).thenReturn(ERROR);
        when(REQUEST_MOCK.getConsoleModel()).thenReturn(CONSOLE_MODEL_MOCK);

        var exception = new RuntimeException();
        when(CONSOLE_MODEL_MOCK.getAttributes("errorData")).thenReturn(exception);

        when(CONSOLE_PROPERTIES_MOCK.isDetailErrorMessage()).thenReturn(false);

        var errorDataMock = new SimpleErrorMessageMapper.ErrorData("에러명", "에러 메시지");
        when(SIMPLE_ERROR_MESSAGE_MAPPER_MOCK.toErrorData(exception)).thenReturn(errorDataMock);

        var modelAndView = ERROR_HANDLER.handleRequest(REQUEST_MOCK);

        verify(CONSOLE_MODEL_MOCK).addAttributes("errorMessage", errorDataMock.message());
        verify(CONSOLE_MODEL_MOCK).addAttributes("errorName", errorDataMock.name());

        assertThat(modelAndView.getView(), is(REQUEST_MOCK.getCommand().getViewName()));
    }
}