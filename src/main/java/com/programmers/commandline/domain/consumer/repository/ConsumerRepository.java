package com.programmers.commandline.domain.consumer.repository;

import com.programmers.commandline.domain.consumer.entity.Consumer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsumerRepository {

    Consumer insert(Consumer customer);

    Consumer update(Consumer customer);

    // Customer save(Customer customer);

    int count();

    List<Consumer> findAll();

    Optional<Consumer> findById(UUID consumerId);

    Optional<Consumer> findByName(String name);

    Optional<Consumer> findByEmail(String email);

    void deleteAll();

}
