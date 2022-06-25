package ru.petrov.soap.spring.boot.Service;

import localhost.SoapRole;
import localhost.SoapUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petrov.soap.spring.boot.Entity.SoapRoleEntity;
import ru.petrov.soap.spring.boot.Entity.SoapUserEntity;
import ru.petrov.soap.spring.boot.Repository.RoleRepository;
import ru.petrov.soap.spring.boot.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    @Transactional
    public boolean remove(String login) {
        SoapUserEntity soapUserEntity = userRepository.findByLogin(login);
        if (soapUserEntity != null) {
            userRepository.deleteByLogin(login);
            userRepository.delete(soapUserEntity);
        }
        return true;
    }

    @Transactional
    public boolean create(String name, String login, String password, List<SoapRole> roles) {
        if (userRepository.findByLogin(login) != null) return false;
        SoapUserEntity userEntity = new SoapUserEntity();
        userEntity.setName(name);
        userEntity.setLogin(login);
        userEntity.setPassword(password);
        userRepository.save(userEntity);
        saveRoles(roles, userEntity);
        return true;
    }

    public void saveRoles(List<SoapRole> roles, SoapUserEntity userEntity) {
        System.out.println(roles.size());
        if (roles.size() > 0)
            for (SoapRole r : roles) {
                SoapRoleEntity soapRoleEntity = roleRepository.findByName(r.getName());
                List<SoapUserEntity> users = soapRoleEntity.getUsers();
                users.add(userEntity);
                System.out.println("saving roles...");
                roleRepository.save(soapRoleEntity);
            }
    }

    private static final String PASSWORD_PATTERN = "^.*(?=.*[0-9])(?=.*[A-Z]).*$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(final String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public List<String> validatePass(String name, String login, String password) {
        List<String> errors = new ArrayList<>();
        if (name.equals("")) errors.add("invalid name");
        if (login.equals("")) errors.add("invalid login");
        if (!isValid(password)) errors.add("invalid password");
        return errors;
    }


    @Transactional
    public boolean update(String name, String login, String password, List<SoapRole> roles) {
        boolean removed = remove(login);
        if (removed) {
            create(name, login, password, roles);
            return true;
        } else return false;
    }
}
