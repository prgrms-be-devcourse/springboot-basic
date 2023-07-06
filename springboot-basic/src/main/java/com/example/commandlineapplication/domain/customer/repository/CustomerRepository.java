package com.example.commandlineapplication.domain.customer.repository;

import com.example.commandlineapplication.domain.customer.dto.request.CustomerCreateRequest;
import com.example.commandlineapplication.domain.customer.dto.request.CustomerUpdateRequest;
import com.example.commandlineapplication.domain.customer.model.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

  void save(CustomerCreateRequest customerCreateRequest);

  void update(UUID customerId, CustomerUpdateRequest customerUpdateRequest);

  Optional<Customer> findById(UUID customerId);

  List<Customer> findAll();

  void deleteById(UUID customerId);

  void deleteAll();

}
