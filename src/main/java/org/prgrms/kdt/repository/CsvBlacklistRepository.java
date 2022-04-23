package org.prgrms.kdt.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {

  private final ResourceLoader resourceLoader;

  @Value("${path.blacklist}")
  private String fileName;

  public CsvBlacklistRepository(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  @Override
  public List<Customer> findAll() {
    var resource = resourceLoader.getResource(fileName);
    try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
      return reader.lines().skip(1)
          .map(row -> row.split(", "))
          .map(columns -> {
            var customerId = UUID.fromString(columns[0]);
            var name = columns[1];
            var email = columns[2];
            var createdAt = LocalDateTime.parse(columns[3]);
            var lastLoginAt = LocalDateTime.parse(columns[4]);
            return new Customer(customerId, name, email, createdAt, lastLoginAt);
          }).toList();
    } catch (IOException e) {
      return Collections.emptyList();
    }
  }
}