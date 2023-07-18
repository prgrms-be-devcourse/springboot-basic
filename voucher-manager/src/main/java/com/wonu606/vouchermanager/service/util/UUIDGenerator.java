package com.wonu606.vouchermanager.service.util;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UUIDGenerator {

    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
