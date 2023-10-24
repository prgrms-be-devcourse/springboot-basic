package study.dev.spring.customer.infrastructure;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import study.dev.spring.common.exception.GlobalException;
import study.dev.spring.common.utils.FileUtils;
import study.dev.spring.customer.domain.Customer;
import study.dev.spring.customer.domain.CustomerRepository;
import study.dev.spring.customer.infrastructure.dto.CustomerData;
import study.dev.spring.customer.infrastructure.dto.CustomerMapper;

@Repository
public class FileCustomerRepository implements CustomerRepository {

	private final FileUtils fileUtils;
	private final List<Customer> storage;

	public FileCustomerRepository(
		final List<FileUtils> fileUtilsList,
		@Value("${customer.file.path}") final String filePath
	) {
		this.storage = new ArrayList<>();
		this.fileUtils = getFileUtils(fileUtilsList, filePath);
		copyFileData(filePath);
	}

	@Override
	public List<Customer> findAll() {
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
			storage.add(CustomerMapper.toCustomer(customerData));
		});
	}
}
