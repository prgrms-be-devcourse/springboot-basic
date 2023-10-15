package study.dev.spring.common.utils;

public interface FileUtils {

	boolean isSupported(String filePath);

	Object readFile(String filePath);
}
