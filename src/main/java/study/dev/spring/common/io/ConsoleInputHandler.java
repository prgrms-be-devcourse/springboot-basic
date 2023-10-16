package study.dev.spring.common.io;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import study.dev.spring.common.exception.GlobalException;

@Component
public class ConsoleInputHandler implements InputHandler {

	private static final Scanner scanner = new Scanner(System.in);

	@PreDestroy
	public void closeScanner() {
		scanner.close();
	}

	@Override
	public int inputNumber() {
		try {
			return scanner.nextInt();
		} catch (InputMismatchException e) {
			throw new GlobalException(ONLY_NUMBER);
		}
	}

	@Override
	public String inputString() {
		return scanner.next();
	}
}
