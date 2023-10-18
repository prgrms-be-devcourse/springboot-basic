package team.marco.vouchermanagementsystem.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Component
public class Console {
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void printCommandManual() {
        System.out.println("""
                === 쿠폰 관리 프로그램 ===
                exit: 프로그램 종료
                create: 쿠폰 생성
                list: 쿠폰 목록 조회
                """);
    }

    public String readString() {
        System.out.print("> ");
        String input = readLine();
        System.out.println();

        if (Objects.isNull(input)) {
            throw new RuntimeException("입력 과정에서 오류가 발생했습니다.");
        }

        return input;
    }

    public int readInt() {
        return Integer.parseInt(readString());
    }

    public int readInt(String prompt) {
        System.out.println(prompt + "\n");
        return readInt();
    }

    private String readLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("unreadable input");
        }
    }

    public void printVoucherTypes() {
        System.out.println("""
                0: 고정 금액 할인 쿠폰
                1: % 할인 쿠폰
                """);
    }

    public void printError(Exception e) {
        if(e instanceof NumberFormatException) {
            print("숫자를 입력해 주세요.");
        } else {
            print(e.getMessage());
        }

    }

    public void print(Object object) {
        System.out.println(object);
        System.out.println();
    }
}
