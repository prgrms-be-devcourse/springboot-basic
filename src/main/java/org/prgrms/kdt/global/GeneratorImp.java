package org.prgrms.kdt.global;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class GeneratorImp implements Generator{
    private GeneratorImp() {
    }

    @Override
    public UUID generateId() {
        return UUID.randomUUID();
    }

    @Override
    public LocalDateTime generateTime() {
        return LocalDateTime.now();
    }
}
