package com.wonu606.vouchermanager.controller;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UUIDGenerator {
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
