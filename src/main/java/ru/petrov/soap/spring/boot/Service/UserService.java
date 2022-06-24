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
                soapUser.getRole().addAll(soapRoleList);
            }
        }
        return soapUser;
    }
    public SoapUserEntity getSoapUserEntity(SoapUser soapUser) {
        SoapUserEntity soapUserEntity = new SoapUserEntity();
        if (soapUser != null) {
            soapUserEntity.setLogin(soapUser.getLogin());
            soapUserEntity.setName(soapUser.getName());
            soapUserEntity.setPassword(soapUser.getPassword());
            List<SoapRole> soapRoleList = soapUser.getRole();
            List<SoapRoleEntity> listRole = new ArrayList<>();
            for (SoapRole r : soapRoleList) listRole.add(getSoapRoleEntity(r));
            soapUser.getRole().addAll(soapRoleList);
        }
        return soapUserEntity;
    }

    public SoapRole getSoapRole(SoapRoleEntity role) {
        SoapRole soapRole = new SoapRole();
        soapRole.setId(role.getId());
        soapRole.setName(role.getName());
        return soapRole;
    }

    public SoapRoleEntity getSoapRoleEntity(SoapRole role) {
        SoapRoleEntity soapRoleEntity = new SoapRoleEntity();
        soapRoleEntity.setId(role.getId());
        soapRoleEntity.setName(role.getName());
        return soapRoleEntity;
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
       }
       return true;
    }

    public boolean create(String name, String login ,String password, List<SoapRole> roles){
        if(userRepository.findByLogin(login)!=null) return false;
        SoapUserEntity userEntity = new SoapUserEntity();
        userEntity.setName(name);
        userEntity.setLogin(login);
        userEntity.setPassword(password);
        userRepository.save(userEntity);
        saveRoles(roles,userEntity);
        return true;
    }

    public void  saveRoles(List<SoapRole> roles, SoapUserEntity userEntity){
        System.out.println(roles.size());
        if(roles.size()>0)
            for(SoapRole r: roles){
                SoapRoleEntity soapRoleEntity = roleRepository.findByName(r.getName());
                List<SoapUserEntity> users = soapRoleEntity.getUsers();
                users.add(userEntity);
                System.out.println("saving roles...");
                roleRepository.save(soapRoleEntity);
            }
    }
    public boolean validate(String name, String login ,String password){
        return true;
    }

    public boolean update(String name, String login, String password, List<SoapRole> roles) {
        if(remove(login)) return create(name,login,password,roles);
        else return false;
    }
}
