package study.dev.spring.common.utils;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.exception.GlobalException;

@Component
@RequiredArgsConstructor
public class CsvFileUtils implements FileUtils {

	private static final Set<String> EXT = Set.of("csv");

	@Override
	public boolean isSupported(final String filePath) {
		String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
		return EXT.contains(ext);
	}

	@Override
	public <T> List<Object> readFile(final String filePath, final Class<T> type) {
		Resource resource = new PathResource(filePath);

		try (FileReader fileReader = new FileReader(resource.getFile())) {
			return new CsvToBeanBuilder<>(fileReader)
				.withType(type)
				.build()
				.parse();
		} catch (IOException e) {
			throw new GlobalException(FILE_READ_EX);
		}
	}

	@Override
	public void writeFile(final String filePath, final List<Object> data) {
		Resource resource = new PathResource(filePath);
		validateFileIsExist(resource);

		try (FileWriter fileWriter = new FileWriter(resource.getFile())) {
			StatefulBeanToCsv<Object> beanToCsv = new StatefulBeanToCsvBuilder<>(fileWriter).build();

			beanToCsv.write(data);
		} catch (Exception e) {
			throw new GlobalException(FILE_WRITE_EX);
		}
	}

	private void validateFileIsExist(final Resource resource) {
		if (!resource.exists()) {
			throw new GlobalException(FILE_WRITE_EX);
		}
	}
}
