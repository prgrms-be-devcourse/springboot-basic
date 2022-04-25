package org.prgms.management.blacklist.vo;

import java.time.LocalDateTime;
import java.util.UUID;

public record Blacklist(UUID blacklistId,
                        UUID customerId,
                        LocalDateTime createdAt) {
}
