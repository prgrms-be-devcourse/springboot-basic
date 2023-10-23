package com.programmers.springbootbasic.domain.user.application;

import com.programmers.springbootbasic.domain.user.infrastructure.dto.CsvUser;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.util.FileManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final FileManager fileManager;
    private final String fileName;

    public UserService(FileManager fileManager, @Value("${file.user.path}") String fileName) {
        this.fileName = fileName;
        this.fileManager = fileManager;
    }

    public List<UserResponse> findBlacklistedUsers() {
        return fileManager
            .read(fileName, CsvUser.class)
            .stream()
            .map(csvUser -> UserResponse.of(csvUser.toEntity()))
            .toList();
    }

}
