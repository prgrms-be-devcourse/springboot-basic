package com.zerozae.voucher.view;

import com.zerozae.voucher.exception.ExceptionMessage;
import com.zerozae.voucher.validator.InputValidator;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import static java.lang.System.out;

@Component
public class ConsoleView implements Input, Output {

    private static final String INPUT_READ_EXCEPTION_MESSAGE = "입력을 읽을 때 오류가 발생했습니다.";
    private final BufferedReader bufferedReader;

    public ConsoleView() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Long inputNumber() {
        try {
            return InputValidator.validateInputNumber(bufferedReader.readLine());
        } catch (IOException e) {
            throw ExceptionMessage.error(INPUT_READ_EXCEPTION_MESSAGE);
        } catch (ExceptionMessage e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    @Override
    public String inputUuid() {
        try {
            return bufferedReader.readLine();
        }catch (IOException e){
            throw ExceptionMessage.error(INPUT_READ_EXCEPTION_MESSAGE);
        }catch (IllegalArgumentException e) {
            throw ExceptionMessage.error("유효하지 않은 UUID 형식입니다.");
        }
    }

    @Override
    public String inputCommand() {
        return checkInputStringAndGet();
    }

    @Override
    public String inputVoucherType() {
        return checkInputStringAndGet();
    }

    @Override
    public String inputCustomerName() {
        return checkInputStringAndGet();
    }

    @Override
    public String inputCustomerType() {
        return checkInputStringAndGet();
    }

    @Override
    public void printCommand() {
        String command = """
        
        === Select Program ===
        Type exit to 프로그램을 종료합니다.
        Type customer to 회원 프로그램을 실행합니다.
        Type voucher to 바우처 프로그램을 실행합니다.
        Type Wallet to 지갑 프로그램을 실행합니다.
        """;
        out.println(command);
        printPrompt();
    }

    @Override
    public void printInfo(String voucherInfo) {
        out.print(voucherInfo);
    }

    @Override
    public void printSystemMessage(String message) {
        String systemMessage = MessageFormat.format("\n[System] {0}\n", message);
        out.println(systemMessage);
    }

    @Override
    public void printErrorMessage(String message) {
        String systemMessage = MessageFormat.format("\n[System] {0}", message);
        out.println(systemMessage);
    }

    @Override
    public void printVoucherCommand() {
        String command = """
        
        === Voucher Program ===
        Type back to 메인 메뉴로 돌아갑니다.
        Type create to 신규 바우처를 생성합니다.
        Type list to 모든 바우처 목록을 조회합니다.
        Type search to 바우처 아이디로 바우처를 조회합니다.
        Type update to 바우처 정보를 수정합니다.
        Type delete to 바우처 아이디로 바우처를 삭제합니다.
        Type delete_all to 전체 바우처를 삭제합니다.
        """;
        out.println(command);
        printPrompt();
    }

    @Override
    public void printCustomerCommand() {
        String command = """
        
        === Customer Program ===
        Type back to 메인 메뉴로 돌아갑니다.
        Type create to 신규 회원을 생성합니다.
        Type list to 전체 회원 목록을 조회합니다.
        Type blacklist to 블랙리스트 회원 목록을 조회합니다.
        Type search to 회원 ID로 회원을 조회합니다.
        Type update to 회원 정보를 수정합니다.
        Type delete to 회원 아이디로 회원을 삭제합니다.
        Type delete_all to 전체 회원을 삭제합니다.
        """;
        out.println(command);
        printPrompt();
    }

    @Override
    public void printWalletCommand() {
        String command = """
                
        === Wallet Program ===
        Type back to 메인 메뉴로 돌아갑니다.
        Type assign to 특정 회원에게 바우처를 할당합니다.
        Type voucher_list to 특정 회원이 보유한 바우처들을 조회합니다.
        Type Owner to 바우처의 소유자들을 조회합니다..
        Type remove to 회원에게 할당했던 바우처를 회수합니다.
        Type delete_all to 지갑 내역을 모두 삭제합니다.
        """;
        out.println(command);
        printPrompt();
    }

    public void printPrompt() {
        out.print("> ");
    }

    private String checkInputStringAndGet() {
        try {
            return InputValidator.validateInputString(bufferedReader.readLine());
        } catch (IOException e) {
            throw ExceptionMessage.error(INPUT_READ_EXCEPTION_MESSAGE);
        } catch (ExceptionMessage e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }
}
