package org.programmers.kdtspring.ConsoleIO;

public class ExitCommandStrategy implements CommandStrategy {

    @Override
    public void runCommand() {
        System.out.println("프로그램을 종료합니다.");
    }
}
