package org.prgrms.springbootbasic.repository;

import org.prgrms.springbootbasic.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.APPEND;

@Repository
public class FileBlackListRepository implements BlackListRepository {

    private static final Logger logger = LoggerFactory.getLogger(org.prgrms.springbootbasic.repository.FileVoucherRepository.class);

    @Autowired
    private final ResourceLoader resourceLoader;

    public FileBlackListRepository(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void insert(User user) {
        if (findById(user.getUserId()).isPresent()) {
            logger.info("already in list");
            return;
        }
        StringBuffer newVoucherString = new StringBuffer().append("\n")
                .append(user.getUserId()).append(",")
                .append(user.getName());
        try {
            Files.writeString(getPath(), newVoucherString, APPEND);
        } catch (IOException e) {
            logger.error("can't open file");
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(long userId) {
        List<String> lines = getLines();
        if (lines.size() == 0) {
            return Optional.empty();
        }

        return lines.stream()
                .map(line -> line.split(","))
                .filter(userInfo -> Long.parseLong(userInfo[0]) == userId)
                .findFirst()
                .map(this::userInfo2User);
    }

    @Override
    public List<User> findAll() {
        List<String> lines = getLines();
        if (lines.size() == 0) {
            return Collections.emptyList();
        }

        return lines.stream()
                .map(line -> line.split(","))
                .map(this::userInfo2User)
                .collect(Collectors.toList());
    }

    private Path getPath() throws IOException {
        return resourceLoader.getResource("blackListRepository.csv").getFile().toPath();
    }

    private List<String> getLines() {
        try {
            return Files.readAllLines(getPath());
        } catch (IOException e) {
            logger.error("can't read file", e);
            throw new RuntimeException();
        }
    }

    private User userInfo2User(String[] voucherInfoArr) {
        return new User(Long.parseLong(voucherInfoArr[0]), voucherInfoArr[1]);
    }
}
