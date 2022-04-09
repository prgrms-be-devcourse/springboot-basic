package org.prgms.voucherProgram.repository.user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.prgms.voucherProgram.entity.user.User;

public class FileUserRepository {
    public static final String FILE_LOCATION = "./src/main/java/org/prgms/voucherProgram/repository/filedata";
    public static final String FILE_NAME = FILE_LOCATION + "/customer_blacklist.csv";
    private static final String DELIMITER = ",";
    private static final String ERROR_WRONG_FILE = "[ERROR] 올바른 유저 파일이 아닙니다.";
    private static final int USER_ID_INDEX = 0;
    private static final int USER_NAEM_INDEX = 1;

    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(DELIMITER);
                UUID userId = UUID.fromString(splitLine[USER_ID_INDEX].trim());
                String userName = splitLine[USER_NAEM_INDEX].trim();
                users.add(new User(userId, userName));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(ERROR_WRONG_FILE);
        }
        return users;
    }
}
