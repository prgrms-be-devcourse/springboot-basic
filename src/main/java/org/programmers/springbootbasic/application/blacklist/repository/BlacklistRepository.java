package org.programmers.springbootbasic.application.blacklist.repository;

import org.programmers.springbootbasic.application.blacklist.model.Blacklist;

import java.util.List;

public interface BlacklistRepository {
    List<Blacklist> findAll();
}
