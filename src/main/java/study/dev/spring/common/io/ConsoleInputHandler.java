package study.dev.spring.common.io;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.InputMismatchException;
import java.util.Scanner;

import study.dev.spring.common.exception.GlobalException;

public class ConsoleInputHandler implements InputHandler {

	@Override
	public int inputNumber() {
		try (Scanner scanner = new Scanner(System.in)) {
			return scanner.nextInt();
		} catch (InputMismatchException e) {
			throw new GlobalException(ONLY_NUMBER);
		}
	}

	@Override
	public String inputString() {
		try (Scanner scanner = new Scanner(System.in)) {
			return scanner.next();
		}
	}
}
