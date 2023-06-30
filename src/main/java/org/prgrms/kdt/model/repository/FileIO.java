package org.prgrms.kdt.model.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIO {

	private final String filePath = "";

	private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

	public void saveStringToFile(String data) {
		try {
			FileWriter writer = new FileWriter(filePath, true);
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			logger.error("saveStringToFile메서드에서 파일에 문자열 저장 실패");
			logger.error(e.toString());
		}
	}

	public String loadStringFromFile() {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			FileReader reader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
			bufferedReader.close();
			return stringBuilder.toString();
		} catch (IOException e) {
			logger.error("loadStringFromFile메서드에서 파일에 문자열 불러오기 실패");
			logger.error(e.toString());
		}
		return null;
	}
}
