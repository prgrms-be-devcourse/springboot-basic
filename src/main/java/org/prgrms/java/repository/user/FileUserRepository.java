package org.prgrms.java.repository.user;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.user.User;
import org.prgrms.java.exception.UserException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class FileUserRepository implements UserRepository {
    private static final String DATA_PATH = "data";
    private static final String DATA_NAME_FOR_CUSTOMER = "customer.csv";
    private static final String DATA_NAME_FOR_BLACKLIST = "customer_blacklist.csv";

    public FileUserRepository() {
        File path = new File(DATA_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    @Override
    public Optional<User> findById(UUID userId, boolean isBlocked) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
            Optional<String> str = reader.lines()
                    .filter(line -> line.contains(userId.toString()))
                    .findAny();
            if (str.isPresent()) {
                return Optional.of(Mapper.mapToUser(str.get(), isBlocked));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll(boolean isBlocked) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
            return reader.lines()
                    .map((object) -> Mapper.mapToUser(object, isBlocked))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User insert(User user, boolean isBlocked) {
        if (findById(user.getUserId(), false).isPresent() || findById(user.getUserId(), true).isPresent()) {
            throw new UserException(String.format("Already exists user having id %s", user.getUserId()));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked))))) {
            writer.write(user.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private String getDataName(boolean isBlocked) {
        return (isBlocked) ? DATA_NAME_FOR_BLACKLIST : DATA_NAME_FOR_CUSTOMER;
    }
}
