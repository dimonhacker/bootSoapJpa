package ru.petrov.soap.spring.boot.Service;

import localhost.SoapRole;
import localhost.SoapUser;
import org.springframework.stereotype.Service;
import ru.petrov.soap.spring.boot.Entity.SoapRoleEntity;
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
        SoapUserEntity soapUserEntity = userRepository.findByLogin(login);
        return getSoapUser(soapUserEntity, false);
    }

    public SoapUser getSoapUser(SoapUserEntity soapUserEntity, boolean requireRoles) {
        SoapUser soapUser = new SoapUser();
        if (soapUserEntity != null) {
            soapUser.setLogin(soapUserEntity.getLogin());
            soapUser.setName(soapUserEntity.getName());
            soapUser.setPassword(soapUserEntity.getPassword());
            if (requireRoles) {
                List<SoapRole> soapRoleList = new ArrayList<>();
                List<SoapRoleEntity> listRole = soapUserEntity.getRole();
                for (SoapRoleEntity r : listRole) soapRoleList.add(getSoapRole(r));
                soapUser.getRoles().addAll(soapRoleList);
            }
        }
        return soapUser;
    }

    public SoapRole getSoapRole(SoapRoleEntity role) {
        SoapRole soapRole = new SoapRole();
        soapRole.setId(role.getId());
        soapRole.setName(role.getName());
        return soapRole;
    }

    public List<SoapUser> findAll() {
        List<SoapUserEntity> list;
        list = userRepository.findAll();
        List<SoapUser> soapUsers = new ArrayList<>();
        for (SoapUserEntity sue : list) {
            soapUsers.add(getSoapUser(sue, false));
        }
        return soapUsers;
    }
}
