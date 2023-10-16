package devcourse.springbootbasic.repository.user;

import devcourse.springbootbasic.domain.user.User;
import devcourse.springbootbasic.util.CsvFileHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Profile("default")
@Repository
public class CsvUserRepository implements UserRepository {

    private static final String CSV_LINE_TEMPLATE = "%s,%s,%s";
    private final CsvFileHandler<User> csvFileHandler;
    private final Map<UUID, User> userDatabase = new ConcurrentHashMap<>();

    public CsvUserRepository(CsvFileHandler<User> userCsvFileHandler) {
        this.csvFileHandler = userCsvFileHandler;
    }

    @Override
    public List<User> findAllBlacklistedUsers() {
        return userDatabase.values()
                .stream()
                .filter(User::isBlacklisted)
                .toList();
    }

    @PostConstruct
    public void init() {
        Function<String[], User> parser = line -> {
            UUID userId = UUID.fromString(line[0]);
            String name = line[1];
            boolean isBlacklisted = Boolean.parseBoolean(line[2]);

            return User.createUser(userId, name, isBlacklisted);
        };
        List<User> users = csvFileHandler.readListFromCsv(parser, CSV_LINE_TEMPLATE);

        users.forEach(user -> userDatabase.put(user.getId(), user));
    }

    @PreDestroy
    public void destroy() {
        List<User> users = userDatabase.values()
                .stream()
                .toList();
        Function<User, String> serializer = user
                -> String.format(CSV_LINE_TEMPLATE,
                user.getId(), user.getName(), user.isBlacklisted());

        csvFileHandler.writeListToCsv(users, serializer);
    }
}
