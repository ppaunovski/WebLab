package mk.ukim.finki.mk.lab.service;

import mk.ukim.finki.mk.lab.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Set<String> getAllUsernames();

    List<User> findAll();

    User findById(Long userId);
}
