package org.programmers.springbootbasic.blacklist.repository;

import org.programmers.springbootbasic.blacklist.model.Blacklist;

import java.util.List;

public interface BlacklistRepository {
    List<Blacklist> findAll();
}
