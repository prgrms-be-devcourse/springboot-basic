package com.programmers.springbasic.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.programmers.springbasic.constants.ErrorCode;

public class FileUtils {

	private FileUtils() {
	}

	public static List<String> readFile(String filePath) {
		checkFileExistAndCreate(filePath);
		try {
			return Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_READ.getMessage());
		}
	}

	public static void writeFile(String filePath, List<String> lines) {
		isFileExist(filePath);
		try {
			Files.write(Paths.get(filePath), lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE,
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_WRITE.getMessage());
		}
	}

	private static void isFileExist(String filePath) {
		Path path = Paths.get(filePath);
		if (Files.notExists(path))
			throw new RuntimeException(ErrorCode.FILE_NOT_FOUND.getMessage());
	}

	private static void checkFileExistAndCreate(String pathString) {
		Path path = Paths.get(pathString);
		try {
			if (Files.notExists(path.getParent())) {
				Files.createDirectories(path.getParent());
			}
			if (Files.notExists(path)) {
				Files.createFile(path);
			}
		} catch (IOException e) {
			throw new RuntimeException(ErrorCode.FILE_CANNOT_CREATE.getMessage());
		}
	}

}
