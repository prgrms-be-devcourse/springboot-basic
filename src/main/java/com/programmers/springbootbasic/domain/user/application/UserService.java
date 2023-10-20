package com.programmers.springbootbasic.domain.user.application;

import com.programmers.springbootbasic.domain.user.domain.entity.User;
import com.programmers.springbootbasic.domain.user.presentation.dto.UserResponse;
import com.programmers.springbootbasic.util.FileManager;
import com.programmers.springbootbasic.util.FileProperties;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final FileManager fileManager;

    public UserService(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public List<UserResponse> findBlacklistedUsers() {
        return fileManager
            .read(FileProperties.getUserFilePath(), User.class)
            .stream()
            .map(UserResponse::of)
            .toList();
    }

}
