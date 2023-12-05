package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.User;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidCredentialsException;
import mk.ukim.finki.mk.lab.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.mk.lab.repository.UserRepository;
import mk.ukim.finki.mk.lab.service.AuthService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean credentialsInvalid(String username, String password) {
        return username == null || password == null || username.isEmpty() || password.isEmpty();
    }

    @Override
    public User login(String username, String password) {
        if(credentialsInvalid(username, password))
            throw new InvalidArgumentsException();

        return userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidCredentialsException::new);
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname) {
        if (credentialsInvalid(username, password)) {
            throw new InvalidArgumentsException();
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        User user = new User(username, password, name, surname, LocalDate.now());
        return userRepository.save(user);

    }
}
