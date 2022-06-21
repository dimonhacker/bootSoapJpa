package ru.petrov.soap.spring.boot.Service;

import localhost.SoapUser;
import org.springframework.stereotype.Service;
import ru.petrov.soap.spring.boot.Entity.SoapUserEntity;
import ru.petrov.soap.spring.boot.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }





    public SoapUser findByLogin(String login) {
        System.out.println(login);
        SoapUserEntity soapUserEntity = userRepository.findByLogin(login);
        return getSoapUser(soapUserEntity);
    }

    public SoapUser getSoapUser(SoapUserEntity soapUserEntity){
        SoapUser soapUser = new SoapUser();
        if(soapUserEntity!=null){
            soapUser.setLogin(soapUserEntity.getLogin());
            soapUser.setName(soapUserEntity.getName());
            soapUser.setPassword(soapUserEntity.getPassword());
        }
        return soapUser;
    }

    public List<SoapUser> findAll() {
        List<SoapUserEntity> list =new ArrayList<>();
        list = userRepository.findAll();
        List<SoapUser> soapUsers = new ArrayList<>();
        for (SoapUserEntity sue: list) {
            soapUsers.add(getSoapUser(sue));
        }
        return soapUsers;
    }
}
