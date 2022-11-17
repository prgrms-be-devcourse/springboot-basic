package org.prgrms.java.repository.user;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.user.User;
import org.prgrms.java.exception.UserException;
import org.springframework.beans.factory.annotation.Value;
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
    private final String DATA_PATH;
    private final String DATA_NAME_FOR_CUSTOMER;
    private final String DATA_NAME_FOR_BLACKLIST;

    public FileUserRepository(@Value("${prgrms.data.path}") String DATA_PATH, @Value("${prgrms.data.name.customer}") String DATA_NAME_FOR_CUSTOMER, @Value("${prgrms.data.name.blacklist}") String DATA_NAME_FOR_BLACKLIST) {
        try {
            new File(DATA_PATH).mkdirs();
            new File(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME_FOR_CUSTOMER)).createNewFile();
            new File(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME_FOR_BLACKLIST)).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.DATA_PATH = DATA_PATH;
        this.DATA_NAME_FOR_CUSTOMER = DATA_NAME_FOR_CUSTOMER;
        this.DATA_NAME_FOR_BLACKLIST = DATA_NAME_FOR_BLACKLIST;
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked)), true))) {
            writer.write(user.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public long deleteAll(boolean isBlocked) {
        String fileName = MessageFormat.format("{0}/{1}", DATA_PATH, getDataName(isBlocked));
        long count;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            count = reader.lines()
                    .filter(line -> !line.isBlank())
                    .count();
            new FileOutputStream(fileName).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return count;
    }

    private String getDataName(boolean isBlocked) {
        return (isBlocked) ? DATA_NAME_FOR_BLACKLIST : DATA_NAME_FOR_CUSTOMER;
    }
}
