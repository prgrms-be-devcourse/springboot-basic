package org.prgms.management.repository;

import java.util.Map;
import java.util.UUID;

public interface BlackListRepository {
    Map<UUID, String> getAll();
}
