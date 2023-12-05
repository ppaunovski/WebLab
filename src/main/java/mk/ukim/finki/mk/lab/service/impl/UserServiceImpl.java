package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.model.exceptions.UserNotFoundException;
import mk.ukim.finki.mk.lab.repository.UserRepository;
import mk.ukim.finki.mk.lab.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<String> getAllUsernames() {
        return userRepository.findAll().stream()
                .map(u -> u.getUsername())
                .collect(Collectors.toSet());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
