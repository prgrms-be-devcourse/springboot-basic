package org.prgms.voucherProgram.repository.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.prgms.voucherProgram.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class FileUserRepository implements UserRepository {
    private static final String DELIMITER = ",";
    private static final String ERROR_WRONG_FILE = "[ERROR] 올바른 유저 파일이 아닙니다.";
    private static final int USER_ID_INDEX = 0;
    private static final int USER_NAME_INDEX = 1;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String userFilePath;
    private final Map<UUID, User> storage = new ConcurrentHashMap<>();

    public FileUserRepository(@Value("${file.path.blacklist}") String userFilePath) {
        this.userFilePath = userFilePath;
        readBlackUsers();
    }

    private void readBlackUsers() {
        try (BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new ClassPathResource(userFilePath).getInputStream()))) {
            addBlackUsers(storage, bufferedReader);
        } catch (IOException e) {
            logger.error("Fail to find a blacklist file");
            throw new IllegalArgumentException(ERROR_WRONG_FILE);
        }
    }

    private void addBlackUsers(Map<UUID, User> storage, BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] splitLine = line.split(DELIMITER);
            UUID userId = UUID.fromString(splitLine[USER_ID_INDEX].trim());
            String userName = splitLine[USER_NAME_INDEX].trim();
            storage.put(userId, new User(userId, userName));
        }
    }

    @Override
    public List<User> findBlackUsers() {
        return new ArrayList<>(storage.values());
    }
}
