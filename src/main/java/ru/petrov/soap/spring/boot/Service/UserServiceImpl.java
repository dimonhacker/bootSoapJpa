package ru.petrov.soap.spring.boot.Service;

import localhost.SoapRole;
import localhost.SoapUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petrov.soap.spring.boot.Model.Role;
import ru.petrov.soap.spring.boot.Model.User;
import ru.petrov.soap.spring.boot.Repository.RoleRepository;
import ru.petrov.soap.spring.boot.Repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public SoapUser findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        return getSoapUser(user, true);
    }

    public SoapUser getSoapUser(User user, boolean requireRoles) {
        SoapUser soapUser = new SoapUser();
        if (user != null) {
            soapUser.setLogin(user.getLogin());
            soapUser.setName(user.getName());
            soapUser.setPassword(user.getPassword());
            if (requireRoles) {
                List<SoapRole> soapRoleList = new ArrayList<>();
                List<Role> listRole = user.getRole();
                for (Role r : listRole) soapRoleList.add(getSoapRole(r));
                soapUser.getRole().addAll(soapRoleList);
            }
        }
        return soapUser;
    }

    public SoapRole getSoapRole(Role role) {
        SoapRole soapRole = new SoapRole();
        soapRole.setId(role.getId());
        soapRole.setName(role.getName());
        return soapRole;
    }

    public Role getRole(SoapRole soapRole) {
        Role role = new Role();
        role.setId(soapRole.getId());
        role.setName(soapRole.getName());
        return role;
    }


    public List<SoapUser> findAll() {
        List<User> list;
        list = userRepository.findAll();
        List<SoapUser> soapUsers = new ArrayList<>();
        for (User sue : list) {
            soapUsers.add(getSoapUser(sue, false));
        }
        return soapUsers;
    }


    @Override
    @Transactional
    public boolean remove(String login) {
        User user = userRepository.findByLogin(login);
        if (user != null) {
            //userRepository.deleteByLogin(login);
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean create(String name, String login, String password, List<SoapRole> roles) {
        if (userRepository.findByLogin(login) != null) return false;
        User userEntity = new User();
        userEntity.setName(name);
        userEntity.setLogin(login);
        userEntity.setPassword(password);
        userEntity.setRole(getRoles(roles));
        userRepository.save(userEntity);
        return true;
    }

    public List<Role> getRoles(List<SoapRole> roles) {
        List<Role> roleList = new ArrayList<>();
        for (SoapRole r : roles) {
            roleList.add(getRole(r));
        }
        return roleList;
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


    @Override
    @Transactional
    public boolean update(String name, String login, String password, List<SoapRole> roles) {
        boolean removed = remove(login);
        if (removed) {
            create(name, login, password, roles);
            return true;
        } else return false;
    }


}
