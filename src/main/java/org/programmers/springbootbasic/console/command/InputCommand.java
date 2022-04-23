package org.programmers.springbootbasic.console.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InputCommand implements Command {

    HOME("home", "홈 화면으로 돌아갑니다."),
    HELP("help", "모든 명령어 목록을 봅니다."),
    EXIT("exit", "애플리케이션을 종료합니다."),
    CREATE_VOUCHER("create voucher", "바우처를 새로 생성합니다."),
    LIST_VOUCHER("list voucher", "모든 바우처의 목록을 봅니다."),
    CREATE_MEMBER("create member", "회원을 새로 생성합니다."),
    LIST_MEMBER("list member", "모든 바우처의 목록을 봅니다.");

    private final String viewName;
    private final String description;

    public String getCommandInformation() {
        var stringBuilder = new StringBuilder();
        stringBuilder.append("명령어: ");
        stringBuilder.append(this.getViewName());
        stringBuilder.append(" | ");
        stringBuilder.append(this.getDescription());
        return stringBuilder.toString();
    }
}