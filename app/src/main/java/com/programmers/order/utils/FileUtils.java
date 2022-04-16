package com.programmers.order.utils;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FileUtils {

	private static final CsvMapper CSV_MAPPER = new CsvMapper();
	private static final String CURRENT_PATH = System.getProperty("user.dir");
	private static final String CSV_EXTENSION = ".csv";

	private FileUtils() {

	}

	public static CsvMapper getCsvMapper() {
		// note : LocalDateTime issue (https://stackoverflow.com/questions/21384820/is-there-a-jackson-datatype-module-for-jdk8-java-time)
		CSV_MAPPER.registerModule(new JavaTimeModule());
		return CSV_MAPPER;
	}

	public static String getWorkingDirectory() {
		return CURRENT_PATH;
	}

	public static String generateCsvFileName(String resourceName) {
		return resourceName + CSV_EXTENSION;
	}

	public static CsvSchema getSchemaWithOutHeader(Class<?> classType) {
		CsvMapper csvMapper = FileUtils.getCsvMapper();
		return csvMapper.schemaFor(classType)
				.withoutHeader();
	}

	public static CsvSchema getSchemaWithHeader(Class<?> classType) {
		CsvMapper csvMapper = FileUtils.getCsvMapper();
		return csvMapper.schemaFor(classType)
				.withHeader();
	}

}
