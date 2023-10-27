package team.marco.vouchermanagementsystem.repository;

import team.marco.vouchermanagementsystem.model.Customer;

import java.util.List;

public interface BlacklistRepository {
    List<Customer> findAll();
}
