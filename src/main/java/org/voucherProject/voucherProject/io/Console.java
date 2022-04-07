package org.voucherProject.voucherProject.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Slf4j
@Component
public class Console implements Input, Output{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String input) {
        System.out.println(input);
        return scanner.nextLine();
    }

    @Override
    public void errorMessage() {
        log.error("잘못된 입력입니다.");
    }

    @Override
    public void completeMessage() {
        log.info("완료되었습니다.");
    }

    @Override
    public void endMessage() {
        log.info("시스템을 종료합니다.");
    }
}
