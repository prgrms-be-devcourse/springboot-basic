package com.example.voucher.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import static com.example.voucher.exception.ErrorMessage.FILE_READ_ERROR;
import static com.example.voucher.exception.ErrorMessage.FILE_WRITE_ERROR;

public class FileUtils {

	public static final List<String> readFile(String path) {
		try {
			return Files.readAllLines(new File(path).toPath());
		} catch (IOException e) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(FILE_READ_ERROR.getMessage());
		}
	}

	public static final void writeFile(String path, String string) {
		try (FileWriter fw = new FileWriter(path, true);
		     BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(string);
		} catch (IOException e) {
			// TODO: 로그 남기기
			throw new IllegalArgumentException(FILE_WRITE_ERROR.getMessage());
		}
	}

	public static final void deleteFile(String path) {
		new File(path).delete();
	}
}