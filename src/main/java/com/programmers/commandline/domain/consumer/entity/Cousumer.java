package com.programmers.commandline.domain.consumer.entity;

import java.util.UUID;

public class Cousumer {
    private final Long cousumerId;
    private final String nickName;

    public Cousumer(Long cousumerId, String nickName) {
        this.cousumerId = cousumerId;
        this.nickName = nickName;
    }

    public Long getCousumerId() {
        return this.cousumerId;
    }

    public String getNickName() {
        return this.nickName;
    }
}
