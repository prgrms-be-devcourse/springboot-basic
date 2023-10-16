package devcourse.springbootbasic.repository.user;

import devcourse.springbootbasic.domain.user.User;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, User> userDatabase = new ConcurrentHashMap<>();

    @Override
    public List<User> findAllBlacklistedUsers() {
        return userDatabase.values()
                .stream()
                .filter(User::isBlacklisted)
                .toList();
    }

    @PostConstruct
    public void init() {
        User user1 = User.createUser(UUID.randomUUID(), "not blacklist ogu", false);
        User user2 = User.createUser(UUID.randomUUID(), "blacklist platypus", true);
        userDatabase.put(user1.getId(), user1);
        userDatabase.put(user2.getId(), user2);
    }
}
