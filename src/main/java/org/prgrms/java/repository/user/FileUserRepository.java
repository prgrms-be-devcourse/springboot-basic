package org.prgrms.java.repository.user;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.user.User;
import org.prgrms.java.exception.UserException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.io.*;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class FileUserRepository implements UserRepository {
    private final String DATA_PATH = "data";
    private final String DATA_NAME_FOR_CUSTOMER = "customer.csv";
    private final String DATA_NAME_FOR_BLACKLIST = "customer_blacklist.csv";
    private final BufferedWriter writer;
    private final BufferedReader reader;
    private final BufferedWriter black_writer;
    private final BufferedReader black_reader;

    public FileUserRepository() throws IOException {
        File path = new File(DATA_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }

        writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME_FOR_CUSTOMER), true));
        reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME_FOR_CUSTOMER)));
        black_writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME_FOR_BLACKLIST), true));
        black_reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME_FOR_BLACKLIST)));
    }

    @PreDestroy
    private void close() {
        try {
            writer.close();
            reader.close();
            black_writer.close();
            black_reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(UUID userId, boolean isBlocked) {
        if (isBlocked) {
            return Optional.of(
                    Mapper.mapToUser(
                            black_reader.lines()
                                    .filter(line -> line.contains(userId.toString()))
                                    .findAny()
                                    .orElseThrow(), true));
        }
        return Optional.of(
                Mapper.mapToUser(
                        reader.lines()
                                .filter(line -> line.contains(userId.toString()))
                                .findAny()
                                .orElseThrow(), false));
    }

    @Override
    public Collection<User> findAll(boolean isBlocked) {
        if (isBlocked) {
            return black_reader.lines()
                    .map((String object) -> Mapper.mapToUser(object, true))
                    .collect(Collectors.toList());
        }
        return reader.lines()
                .map((String object) -> Mapper.mapToUser(object, false))
                .collect(Collectors.toList());
    }

    @Override
    public User insert(User user, boolean isBlocked) {
        if (findById(user.getUserId(), false).isPresent() || findById(user.getUserId(), true).isPresent()) {
            throw new UserException(String.format("Already exists user having id %s", user.getUserId()));
        }
        try {
            if (isBlocked) {
                black_writer.write(user.toString());
                black_writer.newLine();
                black_writer.flush();
            }
            writer.write(user.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
