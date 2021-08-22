package org.prgrms.kdt.repository.user;

import org.prgrms.kdt.domain.user.User;
import org.prgrms.kdt.domain.user.UserType;
import org.prgrms.kdt.io.file.IO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileUserRepository implements UserRepository{

    private static final String SEPARATOR = ",";
    private final IO io;

    public FileUserRepository(@Qualifier("csvFileIo") IO io) {
        this.io = io;
    }

    @Override
    public Optional<User> findById(UUID userId) {
        User user = null;
        try {
            String line;
            while((line = io.readLine()) != null) {
                user = createUser(line);

                if (userId.equals(user.getId())) {
                    break;
                }
            }

        } catch (IOException e) {
                e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = null;
        try {
            String line;
            while((line = io.readLine()) != null) {
                user = createUser(line);

                if (email.equals(user.getEmail())) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try {
            String line;
            while ((line = io.readLine()) != null) {
                User user = createUser(line);
                list.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        io.reset();
        return list;
    }

    public List<User> findBlackListedUsers() {
        List<User> list = new ArrayList<>();
        try {
            String line;
            while ((line = io.readLine()) != null) {
                User user = createUser(line);
                if (user.isBlackUser())
                    list.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        io.reset();
        return list;
    }


    private User createUser(String line) {
        String[] splitLine = line.split(SEPARATOR);
        UUID id = convertStrIdToUUID(splitLine[0]);
        String email = splitLine[1];
        String name = splitLine[2];
        UserType userType = convertStrTypeToUserType(splitLine[3]);
        return new User(id, email, name, userType);
    }

    private UserType convertStrTypeToUserType(String userType) {
        return UserType.valueOf(userType);
    }

    private UUID convertStrIdToUUID(String userId) {
        return UUID.fromString(userId);
    }

}
