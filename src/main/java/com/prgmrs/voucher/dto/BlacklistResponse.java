package com.prgmrs.voucher.dto;

import java.util.Map;
import java.util.UUID;

public class BlacklistResponse {
    private final Map<UUID, String> blacklist;

    public BlacklistResponse(Map<UUID, String> blacklist) {
        this.blacklist = blacklist;
    }

    public Map<UUID, String> getBlacklist() {
        return blacklist;
    }
}
