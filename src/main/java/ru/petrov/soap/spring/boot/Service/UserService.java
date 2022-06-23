package ru.petrov.soap.spring.boot.Service;

import localhost.SoapRole;
import localhost.SoapUser;
import org.springframework.stereotype.Service;
import ru.petrov.soap.spring.boot.Entity.SoapRoleEntity;
import ru.petrov.soap.spring.boot.Entity.SoapUserEntity;
import ru.petrov.soap.spring.boot.Repository.RoleRepository;
import ru.petrov.soap.spring.boot.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public SoapUser findByLogin(String login) {
        SoapUserEntity soapUserEntity = userRepository.findByLogin(login);
        return getSoapUser(soapUserEntity, true);
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



    public boolean remove(String login){
        SoapUserEntity soapUserEntity = userRepository.findByLogin(login);
        if(soapUserEntity!=null) {
            userRepository.delete(soapUserEntity);
            return true;
        }
        else  return false;
    }
}
