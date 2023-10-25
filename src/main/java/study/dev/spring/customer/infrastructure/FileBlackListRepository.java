package study.dev.spring.customer.infrastructure;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import study.dev.spring.common.exception.GlobalException;
import study.dev.spring.common.utils.FileUtils;
import study.dev.spring.customer.application.BlackListRepository;
import study.dev.spring.customer.application.dto.CustomerData;

@Component
public class FileBlackListRepository implements BlackListRepository {

	private final FileUtils fileUtils;
	private final List<CustomerData> storage;

	public FileBlackListRepository(
		final List<FileUtils> fileUtilsList,
		@Value("${customer.file.path}") final String filePath
	) {
		this.storage = new ArrayList<>();
		this.fileUtils = getFileUtils(fileUtilsList, filePath);
		copyFileData(filePath);
	}

	@Override
	public List<CustomerData> findAll() {
		return storage.stream().toList();
	}

	private FileUtils getFileUtils(
		final List<FileUtils> fileUtilsList,
		final String filePath
	) {
		return fileUtilsList.stream()
			.filter(fileUtil -> fileUtil.isSupported(filePath))
			.findFirst()
			.orElseThrow(() -> new GlobalException(UNSUPPORTED_EXT));
	}

	private void copyFileData(final String filePath) {
		List<Object> fileData = fileUtils.readFile(filePath, CustomerData.class);
		fileData.forEach(data -> {
			CustomerData customerData = (CustomerData)data;
			storage.add(customerData);
		});
	}
}
