package team.marco.vouchermanagementsystem.repository;

import team.marco.vouchermanagementsystem.model.User;

import java.util.List;

public interface BlacklistRepository {
    List<User> findAll();
}
