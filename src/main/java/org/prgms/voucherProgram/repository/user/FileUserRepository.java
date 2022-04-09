package org.prgms.voucherProgram.repository.user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.entity.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class FileUserRepository implements UserRepository {
    private static final String FILE_NAME = "src/main/resources/file/customer_blacklist.csv";
    private static final String DELIMITER = ",";
    private static final String ERROR_WRONG_FILE = "[ERROR] 올바른 유저 파일이 아닙니다.";
    private static final int USER_ID_INDEX = 0;
    private static final int USER_NAME_INDEX = 1;

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            addUsers(users, bufferedReader);
        } catch (IOException e) {
            throw new IllegalArgumentException(ERROR_WRONG_FILE);
        }
        return users;
    }

    private void addUsers(List<User> users, BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] splitLine = line.split(DELIMITER);
            UUID userId = UUID.fromString(splitLine[USER_ID_INDEX].trim());
            String userName = splitLine[USER_NAME_INDEX].trim();
            users.add(new User(userId, userName));
        }
    }
}
