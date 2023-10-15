package study.dev.spring.common.utils;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.io.IOException;
import java.util.Set;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

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
	public Object readFile(final String filePath) {
		Resource resource = new ClassPathResource(filePath);

		try {
			CsvMapper csvMapper = new CsvMapper();
			CsvSchema schema = CsvSchema.emptySchema().withHeader();

			return csvMapper
				.readerFor(Object.class)
				.with(schema)
				.readValues(resource.getFile())
				.readAll();
		} catch (IOException e) {
			throw new GlobalException(FILE_READ_EX);
		}
	}
}
