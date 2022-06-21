package ru.petrov.soap.spring.boot.Service;

import ru.petrov.soap.spring.boot.Generated.SoapUser;
import org.springframework.stereotype.Service;
import ru.petrov.soap.spring.boot.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }





    public SoapUser findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
