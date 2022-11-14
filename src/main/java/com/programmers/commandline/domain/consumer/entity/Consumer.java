package com.programmers.commandline.domain.consumer.entity;

public record Consumer(Long consumerId, String nickname) {

    @Override
    public String toString() {
        return String.format("ID: %d Nickname: %s\n", this.consumerId, this.nickname);
    }
}
