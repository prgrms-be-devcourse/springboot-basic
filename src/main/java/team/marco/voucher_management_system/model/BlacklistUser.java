package team.marco.voucher_management_system.model;

import static java.text.MessageFormat.format;

import java.util.UUID;

public class BlacklistUser {
    private final UUID id;
    private final String name;

    public BlacklistUser(UUID id, String name) {
        validateName(name);

        this.id = id;
        this.name = name;
    }

    public String getInfo() {
        return format("id: {0}, 고객명: {1} ", id, name);
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 문자열 일 수 없습니다.");
        }
    }
}
