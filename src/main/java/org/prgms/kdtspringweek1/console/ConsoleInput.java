package org.prgms.kdtspringweek1.console;

// 입력의 경우, Scanner, Buffer 또는 다른 방식으로 구현이 이루어질 수 있기에, 인터페이스를 통해 공통 기능을 명시하고 구현
public interface ConsoleInput {

    String getInput();
}
