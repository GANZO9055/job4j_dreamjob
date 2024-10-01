package ru.job4j.dreamjob.service.user;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.repository.user.UserRepository;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private UserRepository userRepository;

    public SimpleUserService(UserRepository sql2oUserRepository) {
        this.userRepository = sql2oUserRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
