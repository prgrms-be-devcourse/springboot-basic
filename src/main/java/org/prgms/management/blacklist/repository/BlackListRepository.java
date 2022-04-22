package org.prgms.management.blacklist.repository;

import org.prgms.management.blacklist.entity.Blacklist;

import java.util.Map;
import java.util.UUID;

public interface BlackListRepository {
    Map<UUID, Blacklist> getAll();
}
