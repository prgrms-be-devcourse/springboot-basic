package study.dev.spring.common.utils;

import java.util.List;

public interface FileUtils {

	boolean isSupported(String filePath);

	<T> List<Object> readFile(String filePath, Class<T> type);

	void writeFile(String filePath, List<Object> data);
}
