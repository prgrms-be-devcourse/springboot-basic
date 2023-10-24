package team.marco.vouchermanagementsystem.model;

import java.util.UUID;

import static java.text.MessageFormat.format;

public class User {
    private final UUID id;
    private final String name;

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getInfo() {
        return format("id: {0}, 고객명: {1} ", id, name);
    }
}
