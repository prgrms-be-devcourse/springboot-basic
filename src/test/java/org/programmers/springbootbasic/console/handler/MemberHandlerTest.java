package org.programmers.springbootbasic.console.handler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.console.model.Model;
import org.programmers.springbootbasic.console.request.ConsoleRequest;
import org.programmers.springbootbasic.member.domain.MemberDto;
import org.programmers.springbootbasic.member.service.MemberService;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.INPUT_AND_REDIRECT;
import static org.programmers.springbootbasic.console.ConsoleResponseCode.PROCEED;
import static org.programmers.springbootbasic.console.command.InputCommand.CREATE_MEMBER;
import static org.programmers.springbootbasic.console.command.InputCommand.LIST_MEMBER;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_MEMBER_COMPLETE;
import static org.programmers.springbootbasic.console.command.RedirectCommand.CREATE_MEMBER_EMAIL;

class MemberHandlerTest {

    private static final MemberService MEMBER_SERVICE_MOCK = mock(MemberService.class);
    private static final ConsoleRequest REQUEST_MOCK = mock(ConsoleRequest.class);
    private static final Model MODEL_MOCK = mock(Model.class);

    private static final MemberHandler MEMBER_HANDLER = new MemberHandler(MEMBER_SERVICE_MOCK);

    @AfterEach
    void resetMock() {
        reset(MEMBER_SERVICE_MOCK, REQUEST_MOCK, MODEL_MOCK);
    }

    @BeforeAll
    static void initializeHandler() {
        MEMBER_HANDLER.initCommandList();
    }

    @Test
    @DisplayName("명령어: CREATE")
    void handleCreateRequest() {
        when(REQUEST_MOCK.getCommand()).thenReturn(CREATE_MEMBER);
        when(REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);

        var modelAndView = MEMBER_HANDLER.handleRequest(REQUEST_MOCK);

        verify(MODEL_MOCK, times(1)).setRedirectLink(CREATE_MEMBER_EMAIL);
        assertThat(modelAndView.getResponseCode(), is(INPUT_AND_REDIRECT));
    }

    @Test
    @DisplayName("명령어: CREATE_EMAIL")
    void handleCreateAmountRequest() {
        when(REQUEST_MOCK.getCommand()).thenReturn(CREATE_MEMBER_EMAIL);
        when(REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);
        when(MODEL_MOCK.getAttributes("name")).thenReturn("tester");

        var modelAndView = MEMBER_HANDLER.handleRequest(REQUEST_MOCK);

        verify(MODEL_MOCK, times(1)).setRedirectLink(CREATE_MEMBER_COMPLETE);
        assertThat(modelAndView.getResponseCode(), is(INPUT_AND_REDIRECT));
    }

    @Test
    @DisplayName("명령어: CREATE_COMPLETE")
    void handleCreateCompleteRequest() {
        when(REQUEST_MOCK.getCommand()).thenReturn(CREATE_MEMBER_COMPLETE);
        when(REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);
        when(MODEL_MOCK.getAttributes("name")).thenReturn("tester");
        when(MODEL_MOCK.getAttributes("email")).thenReturn("email@prgms.com");

        var modelAndView = MEMBER_HANDLER.handleRequest(REQUEST_MOCK);

        verify(MEMBER_SERVICE_MOCK, times(1)).signUp("tester", "email@prgms.com");
        verify(MODEL_MOCK, times(1)).clear();
        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }

    @Test
    @DisplayName("명령어: LIST - 저장된 회원이 없을 때")
    void handleListWithNoVoucher() {
        when(REQUEST_MOCK.getCommand()).thenReturn(LIST_MEMBER);
        when(REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);
        when(MEMBER_SERVICE_MOCK.getAllMembers()).thenReturn(new ArrayList<>());

        var modelAndView = MEMBER_HANDLER.handleRequest(REQUEST_MOCK);

        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }

    @Test
    @DisplayName("명령어: LIST - 저장된 바우처가 있을 때")
    void handleListWithSomeVoucher() {
        when(REQUEST_MOCK.getCommand()).thenReturn(LIST_MEMBER);
        when(REQUEST_MOCK.getModel()).thenReturn(MODEL_MOCK);

        ArrayList<MemberDto> members = new ArrayList<>();
        members.add(new MemberDto(1L, null, null, null, null, new ArrayList<>()));
        members.add(new MemberDto(2L, null, null, null, null, new ArrayList<>()));
        when(MEMBER_SERVICE_MOCK.getAllMembers()).thenReturn(members);

        var modelAndView = MEMBER_HANDLER.handleRequest(REQUEST_MOCK);

        assertThat(modelAndView.getResponseCode(), is(PROCEED));
    }
}
