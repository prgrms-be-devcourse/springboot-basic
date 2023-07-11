package com.prgmrs.voucher.dto.response;

import java.util.Map;
import java.util.UUID;

public record BlacklistResponse(Map<UUID, String> blacklist) {
}
