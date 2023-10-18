package team.marco.vouchermanagementsystem.model;

import java.util.UUID;

import static java.text.MessageFormat.format;

public class User {
    private UUID id;
    private String name;

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public String getInfo() {

        return format("id: {0}, 고객명: {1} ", id, name);
    }
}
