package study.dev.spring.user.infrastructure;

import static study.dev.spring.common.exception.GlobalErrorCode.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import study.dev.spring.common.exception.GlobalException;
import study.dev.spring.common.utils.FileUtils;
import study.dev.spring.user.domain.User;
import study.dev.spring.user.domain.UserRepository;
import study.dev.spring.user.infrastructure.dto.UserData;

@Repository
public class FileUserRepository implements UserRepository {

	private final FileUtils fileUtils;
	private final List<User> storage;

	public FileUserRepository(
		final List<FileUtils> fileUtilsList,
		@Value("${user.file.path}") final String filePath
	) {
		this.storage = new ArrayList<>();
		this.fileUtils = getFileUtils(fileUtilsList, filePath);
		copyFileData(filePath);
	}

	@Override
	public List<User> findAll() {
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
		List<Object> fileData = fileUtils.readFile(filePath, UserData.class);
		fileData.forEach(data -> {
			UserData userData = (UserData)data;
			storage.add(userData.toUser());
		});
	}
}
