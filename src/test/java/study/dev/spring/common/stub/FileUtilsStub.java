package study.dev.spring.common.stub;

import java.util.ArrayList;
import java.util.List;

import study.dev.spring.common.utils.FileUtils;

public class FileUtilsStub implements FileUtils {

	@Override
	public boolean isSupported(String filePath) {
		return true;
	}

	@Override
	public <T> List<Object> readFile(final String filePath, final Class<T> type) {
		return new ArrayList<>();
	}

	@Override
	public void writeFile(final String filePath, final List<Object> data) {
	}
}
