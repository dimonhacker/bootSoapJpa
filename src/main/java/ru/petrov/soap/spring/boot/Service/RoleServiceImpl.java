package ru.petrov.soap.spring.boot.Service;

import org.springframework.stereotype.Service;
import ru.petrov.soap.spring.boot.Model.Role;
import ru.petrov.soap.spring.boot.Repository.RoleRepository;

import javax.annotation.PostConstruct;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    @PostConstruct
    public void initRoles() {
        String[] roles = {"admin", "user", "moderator"};
        for (String roleName : roles) {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }
}
