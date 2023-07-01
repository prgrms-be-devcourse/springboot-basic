package org.prgrms.kdt.model.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileIO {

	private final String fileName;
	private final String dirPath;
	private final File file;

	public FileIO(String fileName, String dirPath) {
		this.fileName = fileName;
		this.dirPath = dirPath;
		this.file = new File(dirPath, fileName);;
	}

	private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

	public void saveStringToFile(String data) {
		try {
			FileWriter writer = new FileWriter(this.file, true);
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
			FileReader reader = new FileReader(this.file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.lineSeparator());
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
