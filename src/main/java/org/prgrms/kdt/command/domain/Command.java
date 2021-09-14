package org.prgrms.kdt.command.domain;

/**
 * Strategy Pattern 을 사용하고자 한다
 * - CommandLineApp는 Client
 * - create, list, exit 등은 Strategy
 * - Command 인터페이스는 Context
 * <p>
 * - 사용 이유 : 행위를 클래스 캡슐화해 동적으로 행위를 자유롭게 바꿀 수 있게 해주기 때문.
 */
@FunctionalInterface
public interface Command {
    boolean execute();
}
