package com.prgmrs.voucher.dto.response;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public record BlacklistResponse(Map<UUID, String> blacklist) {
    @Override
    public String toString() {
        return blacklist.entrySet().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
